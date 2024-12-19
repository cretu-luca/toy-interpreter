package Model.Statement;

import Model.ProgramState;
import Model.Exception.GenericException;
import Model.Expression.*;
import Utils.Dictionary.IMyDictionary;
import Utils.Stack.IMyStack;

public class IfStatement {
    Expression condition;
    IStatement thenBranch;
    IStatement elseBranch;

    public IfStatement(Expression condition, IStatement thenBranch, IStatement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();

        Value conditionValue = condition.evaluate(symbolTable);
        if(!(conditionValue instanceof BooleanValue)) {
            throw new GenericException("IfStatement execute: conditional expression is not a boolean: " + conditionValue.toString());
        }

        BoolValue boolCondition = (BoolValue) conditionValue;

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
