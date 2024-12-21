package Model.State;

import Model.Exception.GenericException;
import Model.Value.*;

public interface IHeapTable {
    Integer allocate(IValue referencedValue);
    void deallocate(Integer address);
    void update(Integer address, IValue referencedValue);
    Boolean isAddressDefined(Integer address);
    IValue get(Integer address) throws GenericException;
}
