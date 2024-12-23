package Model.State;

import java.util.Collection;
import java.util.Map;

import Model.Value.IValue;
import Utils.Dictionary.*;

public class SymbolTable implements ISymbolTable {
    IMyDictionary<String, IValue> dictionary;

    public SymbolTable() {
        this.dictionary = new MyDictionary<String, IValue>();
    }

    @Override
    public void add(String variableName, IValue variableValue) {
        this.dictionary.add(variableName, variableValue);
    }

    @Override
    public void update(String variableName, IValue variableValue) {
        this.dictionary.update(variableName, variableValue);
    }

    @Override
    public IValue get(String variableName) {
        return this.dictionary.get(variableName);
    }

    @Override
    public Boolean isDefined(String variableName) {
       return this.dictionary.isDefined(variableName);
    }
    
    @Override
    public String toString() {
        return this.dictionary.toString();
    }

    @Override
    public Collection<IValue> getValues() {
        return this.dictionary.getValues();
    }

    @Override
    public ISymbolTable deepCopy() {
        ISymbolTable newSymbolTable = new SymbolTable();
        for(String key : this.dictionary.keySet()) {
            IValue value = this.dictionary.get(key);
            newSymbolTable.add(key, value.deepCopy());
        }
        
        return newSymbolTable;
    }

    @Override
    public Map<String, IValue> getContent() {
        return dictionary.getContent();
    }
}
