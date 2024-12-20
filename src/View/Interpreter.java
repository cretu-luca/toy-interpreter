package View;

import Controller.Controller;
import Model.Expression.*;
import Model.State.*;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.*;
import Repository.*;
import View.Command.*;

public class Interpreter {
    private static IStatement createExample1() {
        // int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)

        return new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()), 
                new CompoundStatement(
                    new VariableDeclarationStatement("b", new IntType()),
                    new CompoundStatement(
                        new AssignmentStatement(
                            "a",
                            new ArithmeticExpression(
                                new ValueExpression(new IntValue(2)),
                                    new ArithmeticExpression(
                                        new ValueExpression(new IntValue(3)), 
                                        new ValueExpression(new IntValue(5)), 
                                        "*"),
                                    "+")),
                        new CompoundStatement(
                            new AssignmentStatement(
                                "b",
                                new ArithmeticExpression(
                                        new VariableExpression("a"),
                                        new ValueExpression(new IntValue(1)),
                                        "+"
                                    )   
                                ),

                            new PrintStatement(new VariableExpression("b"))))));
    }

    private static IStatement createExample2() {
        // bool a; int v; a = true; if a then v = 2 else v = 3; print(v)
        return new CompoundStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));
    }

    private static IStatement createExample3() {
        // string file; file = "test.in"; openFile(file); int v; readFile(file, v); print(v); readFile(file, v); print(v); closeFile(file)
        return new CompoundStatement(
            new VariableDeclarationStatement("file", new StringType()),
            new CompoundStatement(
                    new AssignmentStatement("file", new ValueExpression(new StringValue("test.in"))),
                    new CompoundStatement(
                            new OpenFileStatement(new VariableExpression("file")),
                            new CompoundStatement(
                                    new VariableDeclarationStatement("v", new IntType()),
                                    new CompoundStatement(
                                            new ReadFileStatement(new VariableExpression("file"), "v"),
                                            new CompoundStatement(
                                                    new PrintStatement(new VariableExpression("v")),
                                                    new CompoundStatement(
                                                            new ReadFileStatement(new VariableExpression("file"), "v"),
                                                            new CompoundStatement(
                                                                    new PrintStatement(new VariableExpression("v")),
                                                                    new CloseFileStatement(new VariableExpression("file"))))))))));
    }

    private static ProgramState createProgramState(IStatement originalProgram) {
        IExecutionStack executionStack = new ExecutionStack();
        ISymbolTable symbolTable = new SymbolTable();
        IOutput output = new Output();
        IFileTable fileTable = new FileTable();
        
        return new ProgramState(executionStack, symbolTable, output, fileTable, originalProgram);
    }

    private static Controller createController(IStatement stmt, String logFilePath) {
        ProgramState programState = createProgramState(stmt);
        IRepository repository = new Repository(programState, logFilePath);
        return new Controller(repository);
    }

    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        
        try {
            IStatement example1 = createExample1();
            IStatement example2 = createExample2();
            IStatement example3 = createExample3();

            menu.addCommand(new RunCommand("1", "int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)", createController(example1, "log1.txt")));
            menu.addCommand(new RunCommand("2",  "bool a; int v; a = true; if a then v = 2 else v = 3; print(v)", createController(example2, "log2.txt")));
            menu.addCommand(new RunCommand("3",  "string file; file = test.in; openFile(file); int v; readFile(file, v); print(v); readFile(file, v); print(v); closeFile(file), null", createController(example3, "log3.txt")));

            menu.addCommand(new ExitCommand("0", "Exit"));
            
            menu.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}