package ca.bcit.comp2522.lab9.bam;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class QuizApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        final Scene scene;

        // TODO: set scene in ANOTHER method/function
//        stage.setScene(scene);
        stage.setTitle("Quiz App game");
        stage.show();
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}