package View.Command;

public class ExitCommand extends ACommand {

    public ExitCommand(String newKey, String newDesc) {
        super(newKey, newDesc);
    }
    
    @Override
    public void execute() {
        System.exit(0);
    }
}
