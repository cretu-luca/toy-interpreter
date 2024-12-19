package Model.Statement;

import Model.ProgramState;
import Model.Exception.*;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.*;
import Utils.Dictionary.*;
import Utils.Stack.*;

public class IfStatement implements IStatement {
    IExpression condition;
    IStatement thenBranch;
    IStatement elseBranch;

    public IfStatement(IExpression condition, IStatement thenBranch, IStatement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, IValue> symbolTable = state.getSymbolTable();

        IValue conditionValue = condition.evaluate(symbolTable);
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
