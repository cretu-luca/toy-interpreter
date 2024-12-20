package Model.State;

import Model.Statement.*;

public class ProgramState {
    private IExecutionStack executionStack;
    private ISymbolTable symbolTable;
    private IFileTable fileTable;
    private IOutput output;
    
    // it feels more natural for the adt's to use Java types
    // however, program state should not
    // therefore, I implement the ExectutionStack, SymbolTable,... in order to 
    // delegate that responsability to a different class in the hierarchy
    // therefore internal representation of SymbolTable uses String,
    // but it received a StringValue from the ProgramState
    // SymbolTable handles the conversion

    // private IStatement originalProgram;

    public ProgramState(IExecutionStack newExecutionStack,
                        ISymbolTable newSymbolTable,
                        IOutput newOutput,
                        IFileTable newFileTable,
                        IStatement newProgram) {
        this.executionStack = newExecutionStack;
        this.symbolTable = newSymbolTable;
        this.output = newOutput;
        this.fileTable = newFileTable;

        // originalProgram = newProgram;
        this.executionStack.push(newProgram);
    }

    public IExecutionStack getExecutionStack() {
        return this.executionStack;
    }

    public ISymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public IOutput getOutput() {
        return this.output;
    }

    public IFileTable getFileTable() {
        return this.fileTable;
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
               "\n-----------------------------------\n";
    }
}
