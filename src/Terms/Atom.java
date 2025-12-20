package Terms;

public class Atom implements Term {
    String name;

    public Atom(String name){
        this.name = name.toLowerCase();
    }

    @Override
    public String toString(){
        return name;
    }
}