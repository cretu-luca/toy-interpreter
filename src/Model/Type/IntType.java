package Model.Type;

public class IntType implements IType {
    
    @Override
    public boolean equals(Object another) {
        if(another instanceof IntType) {
            return true;
        } else return false;
    }

    public String toString() {
        return "int";
    }
}
