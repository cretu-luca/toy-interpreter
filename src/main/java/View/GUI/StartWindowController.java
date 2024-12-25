package View.GUI;

import View.Program;
import View.ProgramsManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;

public class StartWindowController {

    @FXML
    ListView<Program> programListView;

    ProgramsManager programsManager;

    public void initialize(ProgramsManager newProgramsManager) {
        this.programsManager = newProgramsManager;

        this.programListView.setItems(FXCollections.observableArrayList(
                programsManager.getAllPrograms()
        ));
    }

    @FXML
    public void switchToExecutionScene(ActionEvent actionEvent) throws IOException {
        Program selectedProgram = programListView.getSelectionModel().getSelectedItem();
        GUIMenu.switchToExecutionScene(selectedProgram);
    }
}