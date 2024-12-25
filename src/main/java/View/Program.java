package View;

import Controller.Controller;
import Model.Statement.*;
import Model.State.*;
import Repository.*;

public class Program {
    private final String id;
    private final IStatement statement;
    private final String description;
    private Controller controller;

    public Program(String id, IStatement statement, String description) {
        this.id = id;
        this.statement = statement;
        this.description = description;

        ProgramState programState = createProgramState();
        IRepository repository = new Repository(programState, "log" + id + ".txt");
        this.controller = new Controller(repository);
    }

    private ProgramState createProgramState() {
        IExecutionStack executionStack = new ExecutionStack();
        ISymbolTable symbolTable = new SymbolTable();
        IOutput output = new Output();
        IFileTable fileTable = new FileTable();
        IHeapTable heapTable = new HeapTable();

        return new ProgramState(executionStack, symbolTable, output, fileTable, heapTable, statement);
    }

    public ProgramState getCurrentProgramState() {
        return controller.getRepository().getProgramsList().get(0);
    }

    public Controller getController() {
        return controller;
    }

    public void oneStep() throws Exception {
        controller.oneStepForAllPrograms(controller.getRepository().getProgramsList());
    }

    @Override
    public String toString() {
        return description;
    }
}