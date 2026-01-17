package Handlers;

import Logic.Predicate;
import Logic.Rule;
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

    public static void printFacts(List<Predicate> facts){
        System.out.println("=== Facts ===\n");
        if(facts.isEmpty()){
            System.out.println("(no facts)");
        } else {
            for(Predicate f : facts){
                System.out.println(f);
            }
            System.out.println();
        }
    }

    public static void printRules(List<Rule> rules){
        System.out.println("=== Rules ===\n");
        if(rules.isEmpty()){
            System.out.println("(no rules)");
        } else {
            for(Rule r : rules){
                System.out.println(r);
            }
            System.out.println();
        }
    }

    public static void printHelp(){
        System.out.println("=== Available Commands ===\n");
        System.out.println("!facts       - show all facts");
        System.out.println("!rules       - show all rules");
        System.out.println("!examples    - show examples");
        System.out.println("!q           - quit");
        System.out.println();
    }

    public static void printExamples(){
        System.out.println("=== Usage Examples ===\n");
        System.out.println("Adding facts");
        System.out.println("    parent(john, bob)");
        System.out.println("Adding rules");
        System.out.println("    grandparent(X, Z) :- parent(X, Y), parent(Y, Z)");
        System.out.println("Adding recursion");
        System.out.println("    ancestor(X, Z) :- parent(X, Z)");
        System.out.println("    ancestor(X, Z) :- parent(X, Y), ancestor(Y, Z)");
        System.out.println("Querying");
        System.out.println("    parent(X, bob)");
        System.out.println("    parent(john, Z)");
    }
}
