package Model.State;

import java.util.Collection;
import java.util.Set;

import Model.Exception.GenericException;
import Model.Value.*;
import Utils.Dictionary.IMyDictionary;

public interface IHeapTable {
    Integer allocate(IValue referencedValue);
    void deallocate(Integer address);
    void update(Integer address, IValue referencedValue);
    IMyDictionary<Integer, IValue> getContent();
    void setContent(IMyDictionary<Integer, IValue> newContent);
    Collection<IValue> getValues();
    Boolean isAddressDefined(Integer address);
    IValue get(Integer address) throws GenericException;
    Set<Integer> getAddresses();
}
