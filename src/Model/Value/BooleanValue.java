package Model.Value;

import Model.Type.*;

public class BooleanValue implements IValue {
    private final boolean value;
    
    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public IType getType() {
        return new BooleanType();
    }

    @Override
    public boolean equals(Object another) {
        if(!(another instanceof BooleanValue)) {
            return false;
        }

        BooleanValue anotherValue = (BooleanValue) another;
        return this.value == anotherValue.value;
    }
}
