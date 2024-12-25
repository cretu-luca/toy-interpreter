package View.GUI;

import Model.State.ProgramState;
import Model.Value.IValue;
import View.Program;
import View.ProgramsManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Map;

public class ExecutionWindowController {

    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableListView;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> programStateListView;
    @FXML
    private TableView<Map.Entry<String, IValue>> symbolTableListView;
    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, Integer> addressColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> heapValueColumn;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> variableNameColumn;
    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> variableValueColumn;

    private Program currentProgram;

    @FXML
    public void switchToStartWindow(ActionEvent actionEvent) throws IOException {
        GUIMenu.switchToStartScene();
    }

    public void setCurrentProgram(Program program) {
        this.currentProgram = program;
        updateProgramState();
    }

    @FXML
    public void initialize(ProgramsManager newProgramsManager) {
        this.currentProgram = GUIMenu.getSelectedProgram();

        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        heapValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        variableNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        variableValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        updateProgramState();
    }

    private void updateProgramState() {
        if (currentProgram != null) {
            ProgramState state = currentProgram.getCurrentProgramState();
            heapTableListView.setItems(FXCollections.observableArrayList(state.getHeapTable().getContent().getContent().entrySet()));
            symbolTableListView.setItems(FXCollections.observableArrayList(state.getSymbolTable().getContent().entrySet()));
            outputListView.setItems(FXCollections.observableArrayList(state.getOutput().toString()));
            fileTableListView.setItems(FXCollections.observableArrayList(state.getFileTable().toString()));
            executionStackListView.setItems(FXCollections.observableArrayList(state.getExecutionStack().toString()));
            programStateListView.setItems(FXCollections.observableArrayList(state.getID()));
        }
    }

    @FXML
    public void stepButtonAction(ActionEvent actionEvent) {
        if (currentProgram != null) {
            try {
                currentProgram.oneStep();
                updateProgramState();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            System.out.println("Current program is null");
        }
    }
}
