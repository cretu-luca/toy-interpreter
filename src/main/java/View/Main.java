package View;

import View.CLI.TextMenu;

public class Main {
    public static void main(String[] args) {
        ProgramsManager programsManager = new ProgramsManager();
        TextMenu textMenu = new TextMenu(programsManager);
        textMenu.show();
    }
}