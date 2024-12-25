package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GUI extends Application {

    private static Stage stage;
    private static Scene startScene;
    private static Scene executionScene;

    StartWindowController startWindowController;
    ExecutionWindowController executionWindowController;

    @Override
    public void start(Stage newStage) throws IOException {
        this.executionWindowController = new ExecutionWindowController();

        stage = newStage;

        startScene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/StartWindow.fxml"))));
        executionScene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ExecutionWindow.fxml"))));

        stage.setScene(startScene);
        stage.show();
    }

    public static void switchToStartScene() {
        stage.setScene(startScene);
    }

    public static void switchToExecutionScene() {
        stage.setScene(executionScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}