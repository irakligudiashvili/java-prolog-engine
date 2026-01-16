package Logic;

import Terms.Term;

import java.util.List;

public class Predicate {
    private String name;
    private List<Term> arguments;

    public Predicate(String name, List<Term> arguments){
        this.name = name;
        this.arguments = arguments;
    }

    public String getName(){
        return this.name;
    }

    public List<Term> getArguments(){
        return arguments;
    }

    @Override
    public String toString(){
        return name + arguments;
    }
}
