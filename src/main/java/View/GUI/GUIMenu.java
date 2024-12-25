package View.GUI;

import View.Program;
import View.ProgramsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIMenu extends Application {

    private static Stage stage;
    private static Scene startScene;
    private static Scene executionScene;

    private static ProgramsManager programsManager;
    private static Program selectedProgram;

    @Override
    public void start(Stage newStage) throws IOException {
        programsManager = new ProgramsManager();
        stage = newStage;
        stage.setResizable(false);

        FXMLLoader startLoader = new FXMLLoader(getClass().getResource("/StartWindow.fxml"));
        FXMLLoader executionLoader = new FXMLLoader(getClass().getResource("/ExecutionWindow.fxml"));

        startScene = new Scene(startLoader.load());
        executionScene = new Scene(executionLoader.load());
        executionScene.setUserData(executionLoader);

        StartWindowController startController = startLoader.getController();
        ExecutionWindowController executionController = executionLoader.getController();

        startController.initialize(programsManager);
        executionController.initialize(programsManager);

        stage.setScene(startScene);
        stage.show();
    }

    public static void switchToStartScene() {
        stage.setScene(startScene);
    }

    public static Program getSelectedProgram() {
        return selectedProgram;
    }

    public static void switchToExecutionScene(Program program) {
        if(program == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a program.");
            alert.showAndWait();
        } else {
            selectedProgram = program;
            stage.setScene(executionScene);
            FXMLLoader loader = (FXMLLoader)executionScene.getUserData();
            ExecutionWindowController controller = loader.getController();
            controller.setCurrentProgram(program);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}