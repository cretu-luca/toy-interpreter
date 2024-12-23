package View.Command;

public class ExitCommand extends ACommand {

    public ExitCommand(String newKey, String newDescription) {
        super(newKey, newDescription);
    }
    
    @Override
    public void execute() {
        System.exit(0);
    }
}
