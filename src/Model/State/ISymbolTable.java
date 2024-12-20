package Model.State;

import Model.Value.*;

public interface ISymbolTable {
    void add(StringValue variableName, IValue variableValue);
    void update(StringValue variableName, IValue variableValue);
    IValue get(StringValue variableName);
    Boolean isDefined(StringValue variableName);
} 
