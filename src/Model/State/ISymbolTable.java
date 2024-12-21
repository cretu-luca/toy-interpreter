package Model.State;

import java.util.Collection;

import Model.Value.*;

public interface ISymbolTable {
    void add(String variableName, IValue variableValue);
    void update(String variableName, IValue variableValue);
    Collection<IValue> getValues();
    IValue get(String variableName);
    Boolean isDefined(String variableName);
} 
