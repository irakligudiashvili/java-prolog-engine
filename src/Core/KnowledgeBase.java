package Core;

import Logic.Predicate;
import Terms.Term;
import Terms.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
    private List<Predicate> facts;

    public KnowledgeBase(){
        this.facts = new ArrayList<>();
    }

    public KnowledgeBase(List<Predicate> initialFacts){
        this.facts = new ArrayList<>(initialFacts);
    }

    public void addFact(Predicate fact){
        facts.add(fact);
    }

    private Map<Variable, Term> unify(Predicate pattern, Predicate fact){
        if(!pattern.getName().equals(fact.getName())){
            return null;
        }

        if(pattern.arity() != fact.arity()){
            return null;
        }

        Map<Variable, Term> bindings = new HashMap<>();

        List<Term> patternArgs = pattern.getArguments();
        List<Term> factArgs = fact.getArguments();

        for(int i = 0; i < patternArgs.size(); i++){
            Term pTerm = patternArgs.get(i);
            Term fTerm = factArgs.get(i);

            if(pTerm.isVariable()){
                Variable var = (Variable) pTerm;

                if(bindings.containsKey(var)){
                    if(!bindings.get(var).equals(fTerm)){
                        return null;
                    }
                } else {
                    bindings.put(var, fTerm);
                }
            } else {
                if(!pTerm.equals(fTerm)){
                    return null;
                }
            }
        }

        return bindings;
    }

    public List<Map<Variable, Term>> query(Predicate pattern){
        List<Map<Variable, Term>> results = new ArrayList<>();

        for (Predicate fact : facts){
            Map<Variable, Term> binding = unify(pattern, fact);

            if(binding != null){
                results.add(binding);
            }
        }

        return results;
    }
}
