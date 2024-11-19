package ca.bcit.comp2522.lab9.bam;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class that serves as the entry point for a JavaFX application that displays
 * a quiz game.
 */
public class QuizApp extends Application {
    private static final int DELAY = 1500;
    private static final int EMPTY_ANSWER_DELAY = 2;
    private static final int MAX_QUESTIONS = 10;
    private static final int MIN_QUESTIONS = 0;
    private static final int RESET_TO_ZERO = 0;
    private static final int QUESTION_INCREMENT = 1;

    private int currentQuestionIndex;
    private int score;
    private List<Question> questions;
    private List<Question> quizQuestions;

    // UI Components
    private Label mainLabel;
    private TextField answerField;
    private Button beginButton;
    private Button submitButton;
    private Label scoreLabel;
    private Button restartButton;

    /**
     * The main entry point for the JavaFX application. This method is called after
     * the JavaFX application has been initialized.
     *
     * @param stage primary stage for this application that is going appear on
     *              the screen
     */
    @Override
    public void start(final Stage stage) {
        try {
            questions = Question.getQuestions();

            if (questions.isEmpty()) {
                throw new RuntimeException("No questions found in quiz.txt");

            }
        } catch (final Exception e) {
            showErrorAndExit("Error loading questions: " + e.getMessage());
            return;
        }

        currentQuestionIndex = RESET_TO_ZERO;
        score = RESET_TO_ZERO;

        final Scene scene;
        scene = createQuizScene(stage);

        stage.setTitle("Quiz App Game");
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
        // Marcus: separate instantiation and inicialization.
        // Magic numbers too
        final VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(root, 600, 400);
        final String stylesheet;

        stylesheet = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        scene.getStylesheets().add(stylesheet);

        // Marcus: be polite dumbass
        mainLabel = new Label("Welcome to Da Quiz Faty!!!!");
        mainLabel.getStyleClass().add("score-label");
        mainLabel.setWrapText(true);
        mainLabel.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");

        answerField = new TextField();
        answerField.setPromptText("Enter your answer here");
        answerField.setVisible(false); // Hidden until quiz starts
        answerField.setOnAction(e -> handleSubmit());

        beginButton = new Button("Click me to begin!");
        beginButton.getStyleClass().add("button");
        beginButton.setOnAction(e -> startQuiz());

        submitButton = new Button("Submit");
        submitButton.getStyleClass().add("button");
        submitButton.setVisible(false); // Hidden until quiz starts
        submitButton.setOnAction(e -> handleSubmit());

        scoreLabel = new Label("Score: 0/0");
        scoreLabel.getStyleClass().add("score-label");
        scoreLabel.setVisible(false); // Hidden until quiz starts

        restartButton = new Button("Restart Quiz");
        restartButton.getStyleClass().add("button");
        restartButton.setVisible(false); // Hidden until quiz ends
        restartButton.setOnAction(e -> restartQuiz());

        root.getChildren().addAll(mainLabel, answerField, submitButton, beginButton, scoreLabel, restartButton);

        return scene;
    }

    /**
     * Starts the quiz by selecting 10 random questions and displaying the first question.
     */
    private void startQuiz() {
        // Marcus: avoid repeating code, like in restartQuiz()
        Collections.shuffle(questions);
        if (questions.size() >= MAX_QUESTIONS) {
            quizQuestions = questions.subList(MIN_QUESTIONS, MAX_QUESTIONS);
        } else {
            quizQuestions = questions; // If less than 10 questions, use all
        }

        // Reset quiz variables
        currentQuestionIndex = RESET_TO_ZERO;
        score = RESET_TO_ZERO;

        // Update UI Components
        mainLabel.setText("");
        beginButton.setVisible(false);
        submitButton.setVisible(true);
        answerField.setVisible(true);
        scoreLabel.setVisible(true);
        restartButton.setVisible(false);
        scoreLabel.setText("Score: 0/0");

        // Display the first question
        displayQuestion();
    }

    /**
     * Displays the current question in the main label.
     */
    private void displayQuestion() {
        // Marcus: lines are toooo long
        if (currentQuestionIndex < quizQuestions.size()) {
            Question currentQuestion = quizQuestions.get(currentQuestionIndex);
            mainLabel.setText("Question " + (currentQuestionIndex + QUESTION_INCREMENT) + ": " + currentQuestion.getQuestion());
            answerField.clear();
            scoreLabel.setText("Score: " + score + "/" + currentQuestionIndex);
            answerField.requestFocus();
        } else {
            endQuiz();
        }
    }

    /**
     * Handles the submission of an answer, either via the "Submit" button or the ENTER key.
     */
    private void handleSubmit() {
        final String userAnswer;
        final Question currentQuestion;
        final String correctAnswer;

        userAnswer = answerField.getText().trim();
        currentQuestion = quizQuestions.get(currentQuestionIndex);
        correctAnswer = currentQuestion.getAnswer();

        if (currentQuestionIndex >= quizQuestions.size()) {
            return;
        }

        if (userAnswer.isEmpty()) {
            mainLabel.setText("Please enter an answer before submitting");
            final PauseTransition pause;
            pause = new PauseTransition(Duration.seconds(EMPTY_ANSWER_DELAY));

            pause.setOnFinished(e -> mainLabel.setText("Question " + (currentQuestionIndex + QUESTION_INCREMENT) + ": "
                    + currentQuestion.getQuestion()));
            pause.play();

            return;
        }

        if (userAnswer.trim().equalsIgnoreCase(correctAnswer)) {
            score++;
            mainLabel.setText("Correct!");
        } else {
            mainLabel.setText("Incorrect! The correct answer was: " + correctAnswer);
        }

        currentQuestionIndex++;
        scoreLabel.setText("Score: " + score + "/" + currentQuestionIndex);

        // Delay before showing the next question to allow user to read feedback
        // Using a separate thread to avoid blocking the JavaFX Application Thread
        new Thread(() -> {
            try {
                Thread.sleep(DELAY); // 1.5 seconds delay
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            javafx.application.Platform.runLater(this::displayQuestion);
        }).start();
    }

    /**
     * Ends the quiz by displaying the final score and showing the restart button.
     */
    private void endQuiz() {
        mainLabel.setText("Quiz Completed! Your final score is " + score + " out of " + quizQuestions.size() + ".");
        answerField.setVisible(false);
        submitButton.setVisible(false);
        restartButton.setVisible(true);
        scoreLabel.setText("Final Score: " + score + "/" + quizQuestions.size());
    }

    /**
     * Restarts the quiz by resetting variables and UI components.
     */
    private void restartQuiz() {

        Collections.shuffle(questions);

        if (questions.size() >= MAX_QUESTIONS) {
            quizQuestions = questions.subList(MIN_QUESTIONS, MAX_QUESTIONS);
        } else {
            quizQuestions = questions;
        }

        currentQuestionIndex = RESET_TO_ZERO;
        score = RESET_TO_ZERO;

        mainLabel.setText("");
        restartButton.setVisible(false);
        submitButton.setVisible(true);
        answerField.setVisible(true);
        scoreLabel.setVisible(true);
        scoreLabel.setText("Score: 0/0");

        displayQuestion();
    }

    /**
     * Displays an error message and exits the application.
     *
     * @param message The error message to display.
     */
    private void showErrorAndExit(final String message) {
        System.err.println(message);
        // Marcus: Magic number
        System.exit(1);
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
