package View.CLI;

import Controller.Controller;
import Model.Exception.GenericException;
import View.Program;
import View.ProgramsManager;

import java.util.Scanner;

public class TextMenu {
    private final ProgramsManager programsManager;

    public TextMenu(ProgramsManager programsManager) {
        this.programsManager = programsManager;
    }

    private void printMenu() {
        for (Program program : programsManager.getAllPrograms()) {
            System.out.println(program.toString());
        }
        System.out.println("0: Exit");
    }

    private void executeProgram(Program program) {
        try {
            Controller controller = program.getController();
            controller.allSteps();
        } catch (GenericException e) {
            System.err.println("Execution error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Execution interrupted: " + e.getMessage());
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                printMenu();
                System.out.print("Choose program: ");
                String key = scanner.nextLine();

                if (key.equals("0")) {
                    System.out.println("Exiting...");
                    break;
                }

                Program program = programsManager.getProgram(key);
                if (program != null) {
                    executeProgram(program);
                } else {
                    System.out.println("Invalid program selection!");
                }
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}