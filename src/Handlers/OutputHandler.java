package Handlers;

import Terms.Term;
import Terms.Variable;

import java.util.List;
import java.util.Map;

public class OutputHandler {
    public static void badSyntax(){
        System.out.println("Incorrect syntax");
    }

    public static void printAnswer(List<Map<Variable, Term>> answers){
        if (answers.isEmpty()){
            System.out.println("false");
            return;
        }

        for(Map<Variable, Term> sub : answers){
            if(sub.isEmpty()){
                System.out.println("true");
                continue;
            }

            for(Map.Entry<Variable, Term> e : sub.entrySet()){
                System.out.println(e.getValue());
            }
        }
    }
}
