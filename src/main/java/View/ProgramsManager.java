package View;

import Model.Expression.*;
import Model.Statement.*;
import Model.Type.BooleanType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BooleanValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProgramsManager {
    private final Map<String, Program> programs;

    public ProgramsManager() {
        programs = new LinkedHashMap<>();
        initializePrograms();
    }

    private void initializePrograms() {
        addProgram("1", "int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)", createExample1());
        addProgram("2", "bool a; int v; a = true; if a then v = 2 else v = 3; print(v)", createExample2());
        addProgram("3", "string file; file = test.in; openFile(file); int v; readFile(file, v); print(v); readFile(file, v); print(v); closeFile(file)", createExample3());
        addProgram("4", "Ref int v; new(v, 20); print(readHeap(v)); writeHeap(v,30); print(readHeap(v) + 5);", createExample4());
        addProgram("5", "Ref int v; new(v, 20); Ref int a; new(a, 30); print(readHeap(v))", createExample5());
        addProgram("6", "int v; v = 4; while(v > 0) { print(v), v = v - 1 }; print(v);", createExample6());
        addProgram("7", "fork", createExample7());
    }

    private void addProgram(String key, String description, IStatement statement) {
        programs.put(key, new Program(key, statement, description));
    }

    public Program getProgram(String key) {
        return programs.get(key);
    }

    public Collection<Program> getAllPrograms() {
        return programs.values();
    }

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
}




