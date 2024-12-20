package View.Command;

public abstract class ACommand {
    private final String key;
    private final String description;

    public ACommand(String newKey, String newDescription) { 
        this.key = newKey; 
        this.description = newDescription;
    }
    
    public String getKey(){
        return key;
    }
    
    public String getDescription() { 
        return description;
    }

    public abstract void execute();
}
