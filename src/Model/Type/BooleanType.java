package Model.Type;

import Model.Value.*;

public class BooleanType implements IType {
    
    @Override
    public boolean equals(Object another) {
        if(another instanceof BooleanType) {
            return true;
        } else return false;
    }

    public String toString() {
        return "boolean";
    }

    public IValue defaultValue() {
        return new BooleanValue(false);
    }
}