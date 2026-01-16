package Terms;

public class Variable implements Term{
    private String name;

    public Variable(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean isVariable(){
        return true;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || this.getClass() != obj.getClass()){
            return false;
        }

        Variable variable = (Variable) obj;

        if(this.name.equals(variable.name)){
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
