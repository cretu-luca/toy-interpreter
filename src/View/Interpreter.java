package View;

import Controller.Controller;
import Model.Expression.*;
import Model.State.ProgramState;
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
        // int v; v=2; Print(v)
        IStatement exampleProgram = new CompoundStatement(
            new VariableDeclarationStatement("a", new BooleanType()),
            new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                    new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                    new CompoundStatement(
                        new IfStatement(
                            new VariableExpression("a"),
                            new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                            new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                        ),
                        new PrintStatement(new VariableExpression("v"))
                    )
                )
            )
        );

        IMyStack<IStatement> executionStack = new MyStack<IStatement>();
        IMyDictionary<String, IValue> symbolTable = new MyDictionary<String, IValue>();
        IMyList<IValue> output = new MyList<IValue>();
        
        ProgramState programState = new ProgramState(executionStack, symbolTable, output, exampleProgram);

        IRepository repository = new Repository(programState);
        
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