package Model.Statement;

import Model.ProgramState;
import Model.Exception.GenericException;
import Model.Type.*;
import Utils.Dictionary.IMyDictionary;
import Utils.Stack.IMyStack;

public class AssignmentStatement implements IStatement {
    private final String variableName;
    private final Expression expression;

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();

        if(symbolTable.isDefined(variableName)) {
            Value value = expression.evaluate(symbolTable);
            IType type = symbolTable.get(variableName).getType();

            if(value.getType().equals(type)) {
                symbolTable.update(variableName, value);
            } else throw new GenericException("AssignmentStatement execute: declared type of variable " + this.variableName + " and the type of the assigned expression do not match");
        } else throw new GenericException("the used variable was not delcared before");
    }

    @Override
    public String toString() {
        return this.variableName + " = " + expression.toString();
    }
    
}
