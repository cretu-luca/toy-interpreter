package View.GUI;

import Model.State.ProgramState;
import Model.Value.IValue;
import Utils.Tuple.IMyTuple;
import View.Program;
import View.ProgramsManager;
import javafx.application.Platform;
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
import javafx.util.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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

/*
    @FXML
    private TableView<Map.Entry<Integer, Integer>> countDownLatchTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> latchLocationColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> latchValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> barrierTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> indexColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> valueColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> valuesListColumn;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> lockTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> locationColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> valueColumn;

    @FXML
    private TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> locationColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> valueColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> valuesListColumn;

    @FXML
    private TableView<Map.Entry<Integer, IMyTuple>> toySemaphoreTableView;
    @FXML
    private TableColumn<Map.Entry<Integer, IMyTuple>, Integer> semaphoreIndexColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, IMyTuple>, Integer> semaphoreValueColumn;
    @FXML
    private TableColumn<Map.Entry<Integer, IMyTuple>, String> semaphoreListColumn;
 */

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
            List<Integer> uniqueIds = program.getController().getRepository().getProgramsList().stream()
                    .map(ProgramState::getID)
                    .distinct()
                    .collect(Collectors.toList());

            if (!uniqueIds.isEmpty()) {
                programStateListView.setItems(FXCollections.observableArrayList(uniqueIds));
                updateProgramState(uniqueIds.get(0));
            }
        }
    }

    @FXML
    public void initialize(ProgramsManager newProgramsManager) {
        this.currentProgram = GUIMenu.getSelectedProgram();

        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        heapValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        variableNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        variableValueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        // latch
        // latchLocationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        // latchValueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        // barrier
        // indexColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        // valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getKey()));
        // valuesListColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getValue().toString()));

        // lock
        // locationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        // valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue()));

        // count semaphore
        // locationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        // valueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getKey()));
        // valuesListColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getValue().toString()));

        // toy semaphore
        // semaphoreIndexColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getKey()));
        // semaphoreValueColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getFirst()));
        // semaphoreListColumn.setCellValueFactory(cellData -> {
        //    IMyTuple tuple = cellData.getValue().getValue();
        //    return new SimpleStringProperty(tuple.getSecond().toString());
        // });

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
                // countDownLatchTableView.setItems(FXCollections.observableArrayList(selectedState.getLatchTable().getContent().entrySet()));

                // barrierTableView.setItems(FXCollections.observableArrayList(selectedState.getBarrierTable().getContent().entrySet()));
                // barrierTableView.refresh();

                // lockTableView.setItems(FXCollections.observableArrayList(selectedState.getLockTable().getContent().entrySet()));

                // semaphoreTableView.setItems(FXCollections.observableArrayList(selectedState.getSemaphoreTable().getContent().entrySet()));
                // semaphoreTableView.refresh();

                // toySemaphoreTableView.setItems(FXCollections.observableArrayList(selectedState.getToySemaphoreTable().getContent().entrySet()));
                // toySemaphoreTableView.refresh();

                long distinctCount = currentProgram.getController().getRepository()
                        .getProgramsList().stream()
                        .map(ProgramState::getID)
                        .distinct()
                        .count();
                programStateCounter.setText(String.valueOf(distinctCount));
            }
        }
    }

    @FXML
    public void stepButtonAction(ActionEvent actionEvent) {
        if (currentProgram != null) {
            try {
                currentProgram.oneStep();
                List<ProgramState> programStates = currentProgram.getController().getRepository().getProgramsList();

                List<Integer> uniqueIds = programStates.stream()
                        .map(ProgramState::getID)
                        .distinct()
                        .collect(Collectors.toList());

                programStateListView.setItems(FXCollections.observableArrayList(uniqueIds));
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
                    // countDownLatchTableView.getItems().clear();
                    // barrierTableView.getItems().clear();
                    // lockTableView.getItems().clear();
                    // semaphoreTableView.getItems().clear();
                    // toySemaphoreTableView.getItems().clear();
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
