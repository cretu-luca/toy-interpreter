package Model.Value;

import Model.Type.IType;
import Model.Type.ReferenceType;

public class ReferenceValue implements IValue {
    private final Integer heapAddress;
    private final IType referenceType;

    public ReferenceValue(Integer newHeapAddress, IType newReferenceType) {
        this.heapAddress = newHeapAddress;
        this.referenceType = newReferenceType;
    }

    public Integer getAddress() {
        return this.heapAddress;
    }

    @Override
    public IType getType() {
        return new ReferenceType(referenceType);
    }
}
