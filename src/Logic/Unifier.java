package Logic;

import Terms.Atom;
import Terms.Term;
import Terms.Variable;

import java.util.HashMap;
import java.util.Map;

public class Unifier {
    public static Map<Variable, Term> unify(Predicate a, Predicate b){
        if (!a.getName().equals(b.getName())){
            return null;
        }

        if(a.getArguments().size() != b.getArguments().size()){
            return null;
        }

        Map<Variable, Term> bindings = new HashMap<>();

        for(int i = 0; i < a.getArguments().size(); i++){
            if(!unifyTerms(a.getArguments().get(i), b.getArguments().get(i), bindings)){
                return null;
            }
        }

        return bindings;
    }

    private static boolean unifyTerms(Term t1, Term t2, Map<Variable, Term> bindings){
        if(t1 instanceof Atom && t2 instanceof Atom){
            return t1.equals(t2);
        }

        if(t1 instanceof  Variable){
            return  unifyVariable((Variable) t1, t2, bindings);
        }

        if(t2 instanceof Variable){
            return unifyVariable((Variable) t2, t1, bindings);
        }

        return false;
    }

    private static boolean unifyVariable(Variable var, Term value, Map<Variable, Term> bindings){
        if(bindings.containsKey(var)){
            return unifyTerms(bindings.get(var), value, bindings);
        }

        if(value instanceof Variable && bindings.containsKey(value)){
            return unifyTerms(var, bindings.get(value), bindings);
        }

        bindings.put(var, value);
        return true;
    }
}
