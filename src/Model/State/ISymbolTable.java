package Model.State;

import Model.Value.*;

public interface ISymbolTable {
    void add(String variableName, IValue variableValue);
    void update(String variableName, IValue variableValue);
    IValue get(String variableName);
    Boolean isDefined(String variableName);
} 
