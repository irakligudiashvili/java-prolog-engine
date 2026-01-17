package Handlers;

import Logic.Predicate;
import Logic.Rule;
import Logic.Unifier;
import Terms.Term;
import Terms.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
    private Map<String, List<Predicate>> facts = new HashMap<>();
    private Map<String, List<Rule>> rules = new HashMap<>();

    public void addFact(Predicate p){
        try{
            facts.putIfAbsent(p.getName(), new ArrayList<>());
            facts.get(p.getName()).add(p);
            System.out.println("Fact Added");
        } catch (Exception e){
            System.out.println("Error adding fact: " + e);
        }
    }

    public void addRule(Rule r){
        try{
            String headName = r.getHead().getName();
            rules.putIfAbsent(headName, new ArrayList<>());
            rules.get(headName).add(r);
            System.out.println("Rule Added");
        } catch (Exception e){
            System.out.println("Error adding rule: " + e);
        }
    }

    public List<Predicate> getAllFacts(){
        List<Predicate> all = new ArrayList<>();

        for(List<Predicate> list : facts.values()){
            all.addAll(list);
        }

        return all;
    }

    public List<Rule> getAllRules(){
        List<Rule> all = new ArrayList<>();

        for(List<Rule> list : rules.values()){
            all.addAll(list);
        }

        return all;
    }

    public List<Predicate> getFactsByName(String name){
        return facts.getOrDefault(name, new ArrayList<>());
    }

    public List<Rule> getRulesByHead(String name){
        return rules.getOrDefault(name, new ArrayList<>());
    }

    public List<Map<Variable, Term>> query(Predicate query){
        List<Variable> queryVars = new ArrayList<>();

        for(Term t : query.getArguments()){
            if(t.isVariable()){
                queryVars.add((Variable) t);
            }
        }

        List<Map<Variable, Term>> raw = query(query, 0);
        return projectResults(raw, queryVars);
    }

    private List<Map<Variable, Term>> query(Predicate query, int depth){
        if (depth > 100) {
            return new ArrayList<>();
        }

        List<Map<Variable, Term>> results = new ArrayList<>();

        // Try to match FACTS
        for(Predicate fact : getFactsByName(query.getName())){
            Map<Variable, Term> sub = Unifier.unify(query, fact);

            if(sub != null){
                results.add(sub);
            }
        }

        // Try to match RULES
        for(Rule rule : getRulesByHead(query.getName())){
            Rule freshRule = freshCopy(rule);

            Map<Variable, Term> headSub = Unifier.unify(query, freshRule.getHead());

            if(headSub == null){
                continue;
            }

            List<Map<Variable, Term>> bodySolutions = solveBody(freshRule, headSub, depth + 1);

            for(Map<Variable, Term> sol : bodySolutions){
                results.add(sol);
            }
        }

        return results;
    }

    private List<Map<Variable, Term>> solveBody(Rule rule, Map<Variable, Term> initialSub, int depth){
        List<Map<Variable, Term>> solutions = new ArrayList<>();
        solutions.add(new HashMap<>(initialSub));

        for(Predicate bodyPred : rule.getBody()){
            List<Map<Variable, Term>> newSolutions = new ArrayList<>();

            for(Map<Variable, Term> currentSub : solutions){
                Predicate instantiated = substitute(bodyPred, currentSub);

                List<Map<Variable, Term>> answers = query(instantiated, depth + 1);

                for(Map<Variable, Term> ans : answers){
                    Map<Variable, Term> merged = compose(currentSub, ans);
                    newSolutions.add(merged);
                }
            }

            solutions = newSolutions;
        }

        return solutions;
    }

    private Map<Variable, Term> compose(Map<Variable, Term> s1, Map<Variable, Term> s2){
        Map<Variable, Term> result = new HashMap<>();

        for(Map.Entry<Variable, Term> e : s1.entrySet()){
            result.put(e.getKey(), resolve(e.getValue(), s2));
        }

        for(Map.Entry<Variable, Term> e : s2.entrySet()){
            result.put(e.getKey(), e.getValue());
        }

        return result;
    }

    private Predicate substitute(Predicate p, Map<Variable, Term> sub){
        List<Term> newTerms = new ArrayList<>();

        for(Term t : p.getArguments()){
            if(t.isVariable() && sub.containsKey(t)){
                newTerms.add(sub.get(t));
            } else {
                newTerms.add(t);
            }
        }

        return new Predicate(p.getName(), newTerms);
    }

    private List<Map<Variable, Term>> projectResults(List<Map<Variable, Term>> rawResults, List<Variable> queryVars){
        List<Map<Variable, Term>> projected = new ArrayList<>();

        for(Map<Variable, Term> sub : rawResults){
            Map<Variable, Term> filtered = new HashMap<>();

            for(Variable v : queryVars){
                if(sub.containsKey(v)){
                    filtered.put(v, resolve(sub.get(v), sub));
                }
            }

            projected.add(filtered);
        }

        return projected;
    }

    private Term resolve(Term t, Map<Variable, Term> sub){
        if(t.isVariable() && sub.containsKey(t)){
            return resolve(sub.get(t), sub);
        }

        return t;
    }

    private Rule freshCopy(Rule rule){
        Map<Variable, Variable> renameMap = new HashMap<>();

        Predicate newHead = renamePredicate(rule.getHead(), renameMap);

        List<Predicate> newBody = new ArrayList<>();

        for(Predicate p : rule.getBody()){
            newBody.add(renamePredicate(p, renameMap));
        }

        return new Rule(newHead, newBody);
    }

    private Predicate renamePredicate(Predicate p, Map<Variable, Variable> renameMap){
        List<Term> newTerms = new ArrayList<>();

        for(Term t : p.getArguments()){
            if(t.isVariable()){
                Variable v = (Variable) t;
                renameMap.putIfAbsent(v, new Variable(v.getName() + "_"));
                newTerms.add(renameMap.get(v));
            } else {
                newTerms.add(t);
            }
        }

        return new Predicate(p.getName(), newTerms);
    }
}
