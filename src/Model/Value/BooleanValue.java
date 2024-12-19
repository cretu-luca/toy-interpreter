package Model.Value;

import Model.Type.BooleanType;
import Model.Type.IType;

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
}
