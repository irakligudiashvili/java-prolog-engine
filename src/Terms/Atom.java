package Terms;

public class Atom implements Term{
    private String name;

    public Atom(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean isVariable(){
        return false;
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

        if(this.name.equals(atom.name)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
}
