import Core.KnowledgeBase;
import Logic.Predicate;
import Terms.Atom;
import Terms.Term;
import Terms.Variable;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        KnowledgeBase kb = new KnowledgeBase();

        kb.addFact(new Predicate("parent", new Atom("john"), new Atom("bob")));
        kb.addFact(new Predicate("parent", new Atom("bob"), new Atom("alice")));
        kb.addFact(new Predicate("parent", new Atom("mary"), new Atom("bob")));

        Predicate query1 = new Predicate("parent", new Variable("X"), new Atom("bob"));
        List<Map<Variable, Term>> results1 = kb.query(query1);

        System.out.println("Query: parnet(X, bob)");
        for(Map<Variable, Term> binding : results1){
            System.out.println(binding);
        }
    }
}