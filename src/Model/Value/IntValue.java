package Model.Value;

import Model.Type.*;

public class IntValue implements IValue {
    private final int value;
    
    public IntValue(int newValue) {
        this.value = newValue;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object another) {
        if(!(another instanceof IntValue)) {
            return false;
        }

        IntValue anotherValue = (IntValue) another;
        return this.value == anotherValue.value;
    }

}
