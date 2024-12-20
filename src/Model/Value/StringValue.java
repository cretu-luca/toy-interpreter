package Model.Value;

import Model.Type.*;

public class StringValue implements IValue {
    private final String value;

    public StringValue(String newValue) {
        this.value = newValue;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        if(!(another instanceof StringValue)) {
            return false;
        }

        StringValue anotherValue = (StringValue) another;
        return this.value == anotherValue.value;
    }
}
