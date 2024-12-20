package Model.State;

import Model.Value.IValue;
import Model.Value.StringValue;
import Utils.Dictionary.*;

public class SymbolTable implements ISymbolTable {
    IMyDictionary<String, IValue> dictionary;

    public SymbolTable() {
        this.dictionary = new MyDictionary<String, IValue>();
    }

    @Override
    public void add(StringValue variableName, IValue variableValue) {
        this.dictionary.add(variableName.getValue(), variableValue);
    }

    @Override
    public void update(StringValue variableName, IValue variableValue) {
        this.dictionary.update(variableName.getValue(), variableValue);
    }

    @Override
    public IValue get(StringValue variableName) {
        return this.dictionary.get(variableName.getValue());
    }

    @Override
    public Boolean isDefined(StringValue variableName) {
       return this.dictionary.isDefined(variableName.getValue());
    }
    
    @Override
    public String toString() {
        return this.dictionary.toString();
    }
}
