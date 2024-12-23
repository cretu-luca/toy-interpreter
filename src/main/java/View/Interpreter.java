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

    private static IStatement createExample4() {
        // Ref int v;new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        return new CompoundStatement(
            new VariableDeclarationStatement("v", new ReferenceType(new IntType())), 
            new CompoundStatement(
                new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(
                    new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                    new CompoundStatement(
                        new WriteHeapExpression("v", new ValueExpression(new IntValue(30))),
                        new PrintStatement(new ArithmeticExpression(
                            new ReadHeapExpression(new VariableExpression("v")),
                            new ValueExpression(new IntValue(5)),
                            "+"))
                    )
                )
            )    
        );
    }

    private static IStatement createExample5() {
        // Ref int v; new(v, 20); Ref int a; new(a, 30); print(ReadHeap(v))
        return new CompoundStatement(
            new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
            new CompoundStatement(
                new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(
                    new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                    new CompoundStatement(
                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(30))),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))
                    )
                )
            )
        );
    }

    private static IStatement createExample6() {
        // int v; v = 4; while(v > 0) { print(v), v = v - 1 }; print(v);
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                    new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                    new CompoundStatement(
                        new WhileStatement(
                            new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                            new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), "-"))                    
                            ) 
                        ), 
                        new PrintStatement(new VariableExpression("v"))
                    )
                )
        );
    }

    private static IStatement createExample7() {
        return new CompoundStatement(
            new VariableDeclarationStatement("v", new IntType()),
            new CompoundStatement(
                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                new CompoundStatement(
                    new ForkStatement(
                        new CompoundStatement(
                            new AssignmentStatement("v", 
                                new ArithmeticExpression(
                                    new VariableExpression("v"), 
                                    new ValueExpression(new IntValue(1)), 
                                    "+"
                                )
                            ),
                            new PrintStatement(new VariableExpression("v"))
                        )
                    ),
                    new PrintStatement(new VariableExpression("v"))
                )
            )
        );
    }

    private static ProgramState createProgramState(IStatement originalProgram) {
        IExecutionStack executionStack = new ExecutionStack();
        ISymbolTable symbolTable = new SymbolTable();
        IOutput output = new Output();
        IFileTable fileTable = new FileTable();
        IHeapTable heapTable = new HeapTable();
        
        return new ProgramState(executionStack, symbolTable, output, fileTable, heapTable, originalProgram);
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
            IStatement example4 = createExample4();
            IStatement example5 = createExample5();
            IStatement example6 = createExample6();
            IStatement example7 = createExample7();

            menu.addCommand(new RunCommand("1", "int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)", createController(example1, "log1.txt")));
            menu.addCommand(new RunCommand("2", "bool a; int v; a = true; if a then v = 2 else v = 3; print(v)", createController(example2, "log2.txt")));
            menu.addCommand(new RunCommand("3", "string file; file = test.in; openFile(file); int v; readFile(file, v); print(v); readFile(file, v); print(v); closeFile(file), null", createController(example3, "log3.txt")));
            menu.addCommand(new RunCommand("4", "Ref int v; new(v, 20); print(readHeap(v)); writeHeap(v,30); print(readHeap(v) + 5);", createController(example4, "log4.txt")));
            menu.addCommand(new RunCommand("5", "Ref int v; new(v, 20); Ref int a; new(a, 30); print(readHeap(v))", createController(example5, "log5.txt")));
            menu.addCommand(new RunCommand("6", "int v; v = 4; while(v > 0) { print(v), v = v - 1 }; print(v);", createController(example6, "log6.txt")));
            menu.addCommand(new RunCommand("7", "fork", createController(example7, "log7.txt")));

            menu.addCommand(new ExitCommand("0", "Exit"));
            
            menu.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}