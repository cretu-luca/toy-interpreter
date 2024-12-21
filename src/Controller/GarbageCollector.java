package Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import Utils.Dictionary.*;

import Model.Value.*;

public class GarbageCollector implements IGarbageCollector {
    public IMyDictionary<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, IMyDictionary<Integer, IValue> heap) {
        IMyDictionary<Integer, IValue> newHeap = new MyDictionary<>();
        for (Integer address : heap.keySet()) {
            if (symTableAddr.contains(address)) {
                newHeap.add(address, heap.get(address));
            }
        }
        return newHeap;
    }

    public List<Integer> getAddrressesFromSymbolTable(Collection<IValue> symTableValues) {
        List<Integer> addresses = new ArrayList<>();
        for (IValue value : symTableValues) {
            if (value instanceof ReferenceValue) {
                addresses.add(((ReferenceValue) value).getAddress());
            }
        }
        return addresses;
    }
}