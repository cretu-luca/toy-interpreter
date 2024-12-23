package Controller;

import java.util.Collection;
import java.util.List;

import Model.Value.IValue;
import Utils.Dictionary.IMyDictionary;

public interface IGarbageCollector {
    IMyDictionary<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, IMyDictionary<Integer, IValue> heap);
    List<Integer> getAddrressesFromSymbolTable(Collection<IValue> symTableValues);
}
