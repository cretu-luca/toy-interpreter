package Model.Type;

public class BooleanType implements IType {
    
    @Override
    public boolean equals(Object another) {
        if(another instanceof boolean) {
            return true;
        } else return false;
    }

    public String toString() {
        return "boolean";
    }    
}
