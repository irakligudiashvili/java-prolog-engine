import java.util.*;

public class Main {
    public static void main(String[] args) {
        KnowledgeBase kb = new KnowledgeBase();
        ConsoleInterface ci = new ConsoleInterface(kb);

        ci.start();
    }
}