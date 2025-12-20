import Terms.Atom;
import Terms.Term;
import Terms.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleInterface {
    private Scanner sc = new Scanner(System.in);
    private KnowledgeBase kb;

    public ConsoleInterface(KnowledgeBase kb){
        this.kb = kb;
    }

    public void start(){
        System.out.println("PROLOG Engine");

        while(true){
            System.out.println("\nAwaiting input:");
            String input = sc.nextLine().trim();

            if(input.equalsIgnoreCase("exit")) break;

            if(input.startsWith("?")){
                Clause query = parseClause(input.substring(1));
                handleQuery(query);
            } else {
                Clause fact = parseClause(input.replace(".", ""));
                kb.addFact(fact);
                System.out.println("Fact added: " + fact);
            }
        }

        sc.close();
        System.out.println("Goodbye");
    }

    private Clause parseClause(String s){
        s = s.trim();

        String predicate = s.substring(0, s.indexOf("("));
        String argsString = s.substring(s.indexOf("(") + 1, s.indexOf(")"));
        String[] argsArray = argsString.split(",");

        Term[] terms = new Term[argsArray.length];

        for(int i = 0; i < argsArray.length; i++){
            String a = argsArray[i].trim();

            if(Character.isUpperCase(a.charAt(0))){
                terms[i] = new Variable(a);
            } else {
                terms[i] = new Atom(a);
            }
        }

        return new Clause(predicate, terms);
    }

    private void handleQuery(Clause query){
        System.out.println("Query: " + query);
        boolean anyResult = false;
        for(Clause fact : kb.getClauses()){
            boolean success = unifyAndPrint(fact, query);

            if(success){
                anyResult = true;
            }

            if(!anyResult){
                System.out.println("No results");
            }
        }
    }

    boolean unifyAndPrint(Clause fact, Clause query) {
        if (!fact.predicate.equals(query.predicate) || fact.args.length != query.args.length)
            return false;

        Map<String, Term> bindings = new HashMap<>();
        for (int i = 0; i < fact.args.length; i++) {
            Term qArg = query.args[i];
            Term fArg = fact.args[i];
            if (qArg instanceof Variable) {
                bindings.put(((Variable) qArg).name, fArg);
            } else if (qArg instanceof Atom) {
                if (!qArg.toString().equals(fArg.toString())) return false;
            }
        }

        for (Map.Entry<String, Term> entry : bindings.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        return true;
    }
}
