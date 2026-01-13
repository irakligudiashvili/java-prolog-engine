package Logic;

import Terms.Term;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Predicate {
    private final String name;
    private final List<Term> arguments;

    public Predicate(String name, Term... arguments){
        this.name = name.toLowerCase();
        this.arguments = Arrays.asList(arguments);
    }

    public String getName(){
        return name;
    }

    public List<Term> getArguments(){
        return arguments;
    }

    public int arity(){
        return arguments.size();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("(");

        for(int i = 0; i < arguments.size(); i++){
            sb.append(arguments.get(i));
            if(i < arguments.size() - 1){
                sb.append(", ");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || this.getClass() != obj.getClass()){
            return false;
        }

        Predicate predicate = (Predicate) obj;

        if(this.name.equals(predicate.getName()) && this.arguments.equals(predicate.getArguments())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, arguments);
    }
}
