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
            Map<Variable, Term> headSub = Unifier.unify(query, rule.getHead());

            if(headSub == null){
                continue;
            }

            List<Map<Variable, Term>> bodySolutions = solveBody(rule, headSub);

            results.addAll(bodySolutions);
        }

        return projectResults(results, queryVars);
    }

    private List<Map<Variable, Term>> solveBody(Rule rule, Map<Variable, Term> initialSub){
        List<Map<Variable, Term>> solutions = new ArrayList<>();
        solutions.add(new HashMap<>(initialSub));

        for(Predicate bodyPred : rule.getBody()){
            List<Map<Variable, Term>> newSolutions = new ArrayList<>();

            for(Map<Variable, Term> currentSub : solutions){
                Predicate instantiated = substitute(bodyPred, currentSub);

                List<Map<Variable, Term>> answers = query(instantiated);

                for(Map<Variable, Term> ans : answers){
                    Map<Variable, Term> merged = new HashMap<>(currentSub);
                    merged.putAll(ans);
                    newSolutions.add(merged);
                }
            }

            solutions = newSolutions;
        }

        return solutions;
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
                    filtered.put(v, sub.get(v));
                }
            }

            projected.add(filtered);
        }

        return projected;
    }
}
