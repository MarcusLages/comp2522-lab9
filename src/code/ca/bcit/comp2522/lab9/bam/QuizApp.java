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
    /**
     * Creates and returns the quiz scene for the application.
     *
     * @param stage the primary stage of the application
     * @return the constructed quiz scene
     */
    private Scene createQuizScene(final Stage stage) {
        final VBox root = new VBox();
        final Scene scene = new Scene(root, 600, 400);
        final Label label;
        final TextField textField;
        final Button button;
        final Button buttonSubmit;
        final Label scoreLabel;
        final String stylesheet;

        // Add a stylesheet to the scene
        stylesheet = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        scene.getStylesheets().add(stylesheet);

        // Style the root layout
        root.getStyleClass().add("vbox");

        // Create a label to display the welcome message
        label = new Label("Welcome to Da Quiz Faty!!!!");
        label.getStyleClass().add("score-label");

        // Create a text field for user input
        textField = new TextField();

        // Create the "Begin" button
        button = new Button("Click me to begin!");
        button.getStyleClass().add("button");

        // Set the action for the "Begin" button
        button.setOnAction(e -> label.setText("question go here"));

        // Create the "Submit" button
        buttonSubmit = new Button("Submit");
        buttonSubmit.getStyleClass().add("button");

        // Create a label to display the score
        scoreLabel = new Label("Score: 0");
        scoreLabel.getStyleClass().add("score-label");

        // Add components to the layout
        root.getChildren().addAll(label, textField, buttonSubmit, button, scoreLabel);

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