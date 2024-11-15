package ca.bcit.comp2522.lab9.bam;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

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

        // TODO: set scene in ANOTHER method/function
//        stage.setScene(scene);
        stage.setTitle("Quiz App game");
        stage.show();
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