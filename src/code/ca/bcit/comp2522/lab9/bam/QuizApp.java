package ca.bcit.comp2522.lab9.bam;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class QuizApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        final Scene scene;
        final List<Question> questions;
        questions = Question.getQuestions();

        // TODO: set scene in ANOTHER method/function
//        stage.setScene(scene);
        stage.setTitle("Quiz App game");
        stage.show();
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}