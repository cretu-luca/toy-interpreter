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
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExecutionWindowController {
    @FXML
    public Text programStateCounter;
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
        if (program != null && !program.getController().getRepository().getProgramsList().isEmpty()) {
            int defaultProgramStateId = program.getController().getRepository().getProgramsList().get(0).getID();
            updateProgramState(defaultProgramStateId);
        }
    }

    @FXML
    public void initialize(ProgramsManager newProgramsManager) {
        this.currentProgram = GUIMenu.getSelectedProgram();

        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        heapValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        variableNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        variableValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        programStateListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int selectedProgramStateId = newValue;
                updateProgramState(selectedProgramStateId);
            }
        });
    }

    private void updateProgramState(Integer selectedProgramStateId) {
        if (currentProgram != null && selectedProgramStateId != null) {
            ProgramState selectedState = currentProgram.getController().getRepository().getProgramById(selectedProgramStateId);
            if (selectedState != null) {
                heapTableListView.setItems(FXCollections.observableArrayList(selectedState.getHeapTable().getContent().getContent().entrySet()));
                symbolTableListView.setItems(FXCollections.observableArrayList(selectedState.getSymbolTable().getContent().entrySet()));
                outputListView.setItems(FXCollections.observableArrayList(selectedState.getOutput().toString()));
                fileTableListView.setItems(FXCollections.observableArrayList(selectedState.getFileTable().toString()));
                executionStackListView.setItems(FXCollections.observableArrayList(selectedState.getExecutionStack().toString()));
                programStateCounter.setText(String.valueOf(currentProgram.getController().getRepository().getProgramsList().size()));
            }
        }
    }

    @FXML
    public void stepButtonAction(ActionEvent actionEvent) {
        if (currentProgram != null) {
            try {
                currentProgram.oneStep();
                List<ProgramState> programStates = currentProgram.getController().getRepository().getProgramsList();
                programStateListView.setItems(FXCollections.observableArrayList(programStates.stream().map(ProgramState::getID).collect(Collectors.toList())));
                Integer selectedProgramStateId = programStateListView.getSelectionModel().getSelectedItem();

                if (selectedProgramStateId != null) {
                    updateProgramState(selectedProgramStateId);
                }

                if (currentProgram.getController().getRepository().getProgramsList().isEmpty()) {
                    heapTableListView.getItems().clear();
                    symbolTableListView.getItems().clear();
                    outputListView.getItems().clear();
                    fileTableListView.getItems().clear();
                    executionStackListView.getItems().clear();
                    programStateListView.getItems().clear();
                }
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
