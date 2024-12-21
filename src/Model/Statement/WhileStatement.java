package Model.Statement;

import Model.Exception.GenericException;
import Model.Expression.IExpression;
import Model.State.IExecutionStack;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.State.ProgramState;
import Model.Value.BooleanValue;
import Model.Value.IValue;

public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression newExpression, IStatement newStatement) {
        this.expression = newExpression;
        this.statement = newStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();
        IExecutionStack executionStack = state.getExecutionStack();

        IValue expressionValue = this.expression.evaluate(symbolTable, heapTable);
        if(!(expressionValue instanceof BooleanValue)) {
            throw new GenericException("WhileStatement error: " + this.expression + " does not evalute to a boolean value.");
        }

        BooleanValue expressionBooleanValue = (BooleanValue) expressionValue;
        if(expressionBooleanValue.getValue() == true) {
            executionStack.push(this);
            executionStack.push(statement);
        }

        return null;
    } 

    @Override
    public String toString() {
        return "while(" + this.expression + ") " + this.statement;
    }
}
