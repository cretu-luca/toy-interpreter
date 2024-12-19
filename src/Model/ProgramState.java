package Model;

import Model.Statement.IStatement;
import Utils.Dictionary.IMyDictionary;
import Utils.List.IMyList;
import Utils.Stack.IMyStack;

import com.sun.jdi.Value;

public class ProgramState {
    private IMyStack<IStatement> ExecutionStack;
    private IMyDictionary<String, Value> SymbolTable;
    private IMyList<Value> Output;

    private IStatement OriginalProgram;

    public ProgramState(IMyStack<IStatement> ExecutionStack,
                        IMyDictionary<String, Value> SymbolTable,
                        IMyList<Value> Output,
                        IStatement NewProgram) {
        this.ExecutionStack = ExecutionStack;
        this.SymbolTable = SymbolTable;
        this.Output = Output;

        OriginalProgram = NewProgram;
    }

    public IMyStack<IStatement> getExecutionStack() {
        return ExecutionStack;
    }

    public IMyDictionary<String, Value> getSymbolTable() {
        return SymbolTable;
    }

    public IMyList<Value> getOutput() {
        return Output;
    }
}
