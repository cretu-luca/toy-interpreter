package Model.Type;

import Model.Value.*;

public class IntType implements IType {
    
    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    public String toString() {
        return "int";
    }

    public IValue defaultValue() {
        return new IntValue(0);
    }
}