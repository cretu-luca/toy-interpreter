package Model.State;

import Utils.Stack.*;
import Model.Statement.*;

public class ExecutionStack implements IExecutionStack {
    private IMyStack<IStatement> stack;

    public ExecutionStack() {
        this.stack = new MyStack<IStatement>();
    }

    @Override
    public IStatement pop() {
        return this.stack.pop();
    }

    @Override
    public void push(IStatement statement) {
        this.stack.push(statement);
    }

    @Override
    public Boolean isEmpty() {
        return this.stack.isEmpty();    
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

    @Override
    public IExecutionStack deepCopy() {
        IExecutionStack newStack = new ExecutionStack();
        MyStack<IStatement> tempStack = new MyStack<>();
        
        while(!this.isEmpty()) {
            IStatement statement = this.pop();
            tempStack.push(statement);
        }

        while(!tempStack.isEmpty()) {
            IStatement statement = tempStack.pop();
            newStack.push(statement);
            this.push(statement);
        }
        
        return newStack;
    }
}
