package Core;

import Terms.Term;

public class Clause {
    String predicate;
    Term[] args;

    public Clause(String predicate, Term... args){
        this.predicate = predicate.toLowerCase();
        this.args = args;
    }



    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(predicate).append("(");

        for(int i = 0; i < args.length; i++){
            sb.append(args[i]);
            if(i < args.length - 1){
                sb.append(", ");
            }
        }

        sb.append(")");
        return sb.toString();
    }
}
