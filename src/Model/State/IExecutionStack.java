package Model.State;

import Model.Statement.IStatement;

public interface IExecutionStack {
    public IStatement pop();
    public void push(IStatement statement);
    public Boolean isEmpty();
    IExecutionStack deepCopy();
}
