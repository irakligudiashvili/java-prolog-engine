package Terms;

import java.util.Objects;

public class Atom implements Term {
    private final String name;

    public Atom(String name){
        this.name = name.toLowerCase();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean isVariable(){
        return false;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || this.getClass() != obj.getClass()){
            return false;
        }

        Atom atom = (Atom) obj;

        if(this.name.equals(atom.getName())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.name);
    }
}