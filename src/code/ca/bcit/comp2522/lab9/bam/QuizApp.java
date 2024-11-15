package ca.bcit.comp2522.lab9.bam;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
/**
 * Class that serves as the entry point for a JavaFX application that displays
 * a quiz game.
 */
public class QuizApp extends Application {

    /**
     * The main entry point for the JavaFX application. This method is called after
     * the JavaFX application has been initialized.
     *
     * @param stage primary stage for this application that is going appear on
     *              the screen
     */
    @Override
    public void start(final Stage stage) {
        final Scene scene;
        final List<Question> questions;
        questions = Question.getQuestions();
        scene = createQuizScene(stage);

        stage.setTitle("Quiz App game");
        stage.setScene(scene);
        stage.show();
    }
    private Scene createQuizScene(Stage stage) {
        // Create the layout and scene
        final VBox root = new VBox();
        final Scene scene = new Scene(root, 600, 400);

        // Add a stylesheet
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        root.getStyleClass().add("vbox");
        // Create UI components
        Label label = new Label("Welcome to Da Quiz Faty!!!!");
        label.getStyleClass().add("score-label");

        TextField textField =  new TextField();

        Button button = new Button("Click me to begin!");
        button.getStyleClass().add("button");

        Button buttonSubmit = new Button("Submit");
        buttonSubmit.getStyleClass().add("button");

        button.setOnAction(e  -> label.setText("question go here"));
        Label scoreLabel = new Label("Score: 0");

        scoreLabel.getStyleClass().add("score-label");

        // Add components to the layout
        root.getChildren().addAll(label,textField, buttonSubmit, button,scoreLabel);

        // Return the scene
        return scene;
    }


    /**
     * The main method for launching the JavaFX application.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}