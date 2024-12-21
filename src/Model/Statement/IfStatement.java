package Model.Statement;

import Model.Exception.*;
import Model.Expression.*;
import Model.State.*;
import Model.Value.*;

public class IfStatement implements IStatement {
    private final IExpression condition;
    private final IStatement thenBranch;
    private final IStatement elseBranch;

    public IfStatement(IExpression newCondition, IStatement newThenBranch, IStatement newElseBranch) {
        this.condition = newCondition;
        this.thenBranch = newThenBranch;
        this.elseBranch = newElseBranch;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IExecutionStack stack = state.getExecutionStack();
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();

        IValue conditionValue = condition.evaluate(symbolTable, heapTable);
        if(!(conditionValue instanceof BooleanValue)) {
            throw new GenericException("IfStatement execute: conditional expression is not a boolean: " + conditionValue.toString());
        }

        BooleanValue boolCondition = (BooleanValue) conditionValue;

        if(boolCondition.getValue()) {
            stack.push(thenBranch);
        } else {
            stack.push(elseBranch);
        }

        return state;
    }

    @Override
    public String toString() {
        return "(if("+ condition.toString()+") then(" +thenBranch.toString() +") else("+elseBranch.toString()+"))";
    }
}
