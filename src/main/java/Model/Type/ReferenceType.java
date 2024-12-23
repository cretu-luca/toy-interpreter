package Model.Type;

import Model.Value.*;

public class ReferenceType implements IType {
    private final IType type;

    public ReferenceType(IType newType) {
        this.type = newType;
    }

    public IType getType() {
        return this.type;
    }

    public String toString() { 
        return "Ref(" + type.toString() + ")";
    }

    @Override
    public boolean equals(Object another){
        return another instanceof ReferenceType;
    }
    
    public IValue defaultValue() { 
        return new ReferenceValue(0, type);
    }
}
