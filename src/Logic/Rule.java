package Logic;

import java.util.List;

public class Rule {
    private Predicate head;
    private List<Predicate> body;

    public Rule(Predicate head, List<Predicate> body){
        this.head = head;
        this.body = body;
    }

    public Predicate getHead(){
        return this.head;
    }

    public List<Predicate> getBody(){
        return this.body;
    }

    @Override
    public String toString(){
        return this.head.toString() + " - " + this.body;
    }
}
