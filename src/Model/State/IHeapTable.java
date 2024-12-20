package Model.State;

import Model.Exception.GenericException;
import Model.Value.*;

public interface IHeapTable {
    int allocate(IValue referencedValue);
    void deallocate(Integer address);
    IValue get(Integer address) throws GenericException;
}
