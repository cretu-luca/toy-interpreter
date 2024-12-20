package View;

import Controller.Controller;
import Model.Expression.*;
import Model.State.ExecutionStack;
import Model.State.FileTable;
import Model.State.IExecutionStack;
import Model.State.IFileTable;
import Model.State.IOutput;
import Model.State.ISymbolTable;
import Model.State.Output;
import Model.State.ProgramState;
import Model.State.SymbolTable;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.*;
import Repository.*;
import Utils.Dictionary.IMyDictionary;
import Utils.Dictionary.MyDictionary;
import Utils.List.IMyList;
import Utils.List.MyList;
import Utils.Stack.IMyStack;
import Utils.Stack.MyStack;

public class Interpreter {
    private Controller controller;
    
    public Interpreter() {
    IStatement complexExample = new CompoundStatement(
        new VariableDeclarationStatement(new StringValue("fileName"), new StringType()),
        new CompoundStatement(
            new AssignmentStatement(
                new StringValue("fileName"), 
                new ValueExpression(new StringValue("/Users/cretuluca/uni/toy-interpreter/log.txt"))
            ),
            new CompoundStatement(
                new OpenFileStatement(new VariableExpression(new StringValue("fileName"))),
                new CompoundStatement(
                    new VariableDeclarationStatement(new StringValue("x"), new IntType()),
                    new CompoundStatement(
                        new ReadFileStatement(
                            new VariableExpression(new StringValue("fileName")), 
                            new StringValue("x")
                        ),
                        new CompoundStatement(
                            new PrintStatement(new VariableExpression(new StringValue("x"))),
                            new CompoundStatement(
                                new IfStatement(
                                    new ValueExpression(new BooleanValue(true)),
                                    new PrintStatement(new ValueExpression(new StringValue("File read successfully"))),
                                    new PrintStatement(new ValueExpression(new StringValue("File read failed")))
                                ),
                                new CloseFileStatement(new VariableExpression(new StringValue("fileName")))
                            )
                        )
                    )
                )
            )
        )
    );

    IExecutionStack executionStack = new ExecutionStack();
    ISymbolTable symbolTable = new SymbolTable();
    IOutput output = new Output();
    IFileTable fileTable = new FileTable();

    ProgramState programState = new ProgramState(
        executionStack, 
        symbolTable, 
        output, 
        fileTable,
        complexExample
    );

    IRepository repository = new Repository(programState, "log.txt");
    this.controller = new Controller(repository);
}

    public void run() {
        try {
            controller.allSteps();
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}