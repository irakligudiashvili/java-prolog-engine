package Terms;

public class Variable implements Term {
    public String name;

    public Variable(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
