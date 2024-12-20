package View.Command;

import Controller.Controller;
import Model.Exception.GenericException;

public class RunCommand extends ACommand {
    private Controller controller;
    
    public RunCommand(String newKey, String newDesc, Controller controller) {
        super(newKey, newDesc);
        this.controller = controller;
    }
    
    @Override
    public void execute() {
        try {
            controller.allSteps();
        } catch (GenericException e) {
            System.err.println(e.getMessage());
        } 
    }
}