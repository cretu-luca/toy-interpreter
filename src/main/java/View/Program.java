package View;

import Model.State.*;
import Model.Statement.IStatement;
import Controller.*;
import Repository.IRepository;
import Repository.Repository;

public class Program {
    private final IStatement statement;
    private final String key;
    private final String description;
    private Controller controller;

    private static final String LOG_FILE_PATTERN = "log%d.txt";
    public static int fileIndex = 1;

    Program(String newKey, IStatement newStatement, String newDescription) {
        this.statement = newStatement;
        this.description = newDescription;
        this.key = newKey;
    }

    public Controller getController() {
        if (controller == null) {
            controller = createController();
        }
        return controller;
    }

    private Controller createController() {
        ProgramState programState = createProgramState();
        String logFile = String.format(LOG_FILE_PATTERN, fileIndex++);
        IRepository repository = new Repository(programState, logFile);
        return new Controller(repository);
    }

    private ProgramState createProgramState() {
        return new ProgramState(
                new ExecutionStack(),
                new SymbolTable(),
                new Output(),
                new FileTable(),
                new HeapTable(),
                statement
        );
    }

    String getDescription() {
        return this.description;
    }

    IStatement getStatement() {
        return this.statement;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", key, description);
    }
}
