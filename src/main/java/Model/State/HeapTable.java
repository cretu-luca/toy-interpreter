package Model.State;

import Utils.Dictionary.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    public void setContent(IMyDictionary<Integer, IValue> newContent) {
        this.dictionary = newContent;
    }

    @Override
    public IMyDictionary<Integer, IValue> getContent() {
        return this.dictionary;
    }


    @Override
    public Set<Integer> getAddresses() {
        return new HashSet<>(this.dictionary.keySet());
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }

    @Override
    public Collection<IValue> getValues() {
        return this.dictionary.getValues();
    }
}
