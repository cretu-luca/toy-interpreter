package Model.State;

import Utils.Dictionary.*;
import Model.Exception.GenericException;
import Model.Value.*;

public class HeapTable implements IHeapTable {
    private IMyDictionary<Integer, IValue> dictionary;
    private Integer freePosition;
    
    public HeapTable() {
        this.dictionary = new MyDictionary<>();
        this.freePosition = 1;
    }

    @Override
    public Integer allocate(IValue referencedValue) {
        this.dictionary.add(freePosition, referencedValue);
        return freePosition++;
    }

    @Override
    public void update(Integer address, IValue referencedValue) {
        this.dictionary.update(address, referencedValue);
    }
    
    @Override
    public Boolean isAddressDefined(Integer address) {
        return this.dictionary.isDefined(address);
    }

    @Override
    public IValue get(Integer address) throws GenericException {
        return this.dictionary.get(address);
    }

    @Override
    public void deallocate(Integer address) {
        this.dictionary.remove(address);
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }
}
