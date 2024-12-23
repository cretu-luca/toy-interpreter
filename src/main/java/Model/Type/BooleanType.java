package Model.Type;

import Model.Value.*;

public class BooleanType implements IType {
    
    @Override
    public boolean equals(Object another) {
        return another instanceof BooleanType;
    }

    public String toString() {
        return "boolean";
    }

    public IValue defaultValue() {
        return new BooleanValue(false);
    }
}