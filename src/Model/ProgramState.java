package Model;

import Model.Statement.*;
import Model.Value.*;
import Utils.Dictionary.*;
import Utils.List.*;
import Utils.Stack.*;

public class ProgramState {
    private IMyStack<IStatement> executionStack;
    private IMyDictionary<String, IValue> symbolTable;
    private IMyList<IValue> output;

    // private IStatement originalProgram;

    public ProgramState(IMyStack<IStatement> ExecutionStack,
                        IMyDictionary<String, IValue> symbolTable,
                        IMyList<IValue> Output,
                        IStatement newProgram) {
        this.executionStack = ExecutionStack;
        this.symbolTable = symbolTable;
        this.output = Output;

        // originalProgram = newProgram;
        this.executionStack.push(newProgram);
    }

    public IMyStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public IMyDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public IMyList<IValue> getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "\n---------- Program State ----------" +
               "\n=== Execution Stack ===" +
               "\n" + executionStack.toString() + 
               "\n=== Symbol Table ===" +
               "\n" + symbolTable.toString() +
               "\n=== Output ===" +
               "\n" + output.toString() +
               "\n--------------------------------\n";
    }
}
