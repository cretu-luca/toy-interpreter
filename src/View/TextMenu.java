package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import View.Command.ACommand;

public class TextMenu {
    private Map<String, ACommand> commands;
    
    public TextMenu() { 
        this.commands = new HashMap<>();
    }

    public void addCommand(ACommand command) { 
        commands.put(command.getKey(), command);
    }

    private void printMenu() {
        for(ACommand command : commands.values()) {
            String line = String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        try (Scanner scanner = new Scanner(System.in)) {
            while(true){
                printMenu();
                System.out.printf("Input the option: ");
                
                String key = scanner.nextLine();
                ACommand command = commands.get(key);
                
                if (command == null) {
                    System.out.println("Invalid Option");
                    continue; 
                }
                
                command.execute();
            }
        }
    }
}
