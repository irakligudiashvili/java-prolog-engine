package Terms;

import java.util.Objects;

public class Variable implements Term {
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

        Variable variable = (Variable) obj;

        if(this.name.equals(variable.getName())){
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
