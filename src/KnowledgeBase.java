import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase {
    private List<Clause> clauses = new ArrayList<>();

    public void addFact(Clause fact){
        clauses.add(fact);
    }

    public List<Clause> getClauses(){
        return clauses;
    }
}
