package ca.bcit.comp2522.lab9.bam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Question class represents a question and its corresponding answer.
 * It provides functionality to read questions from a file and validate the input.
 */
public class Question {

    private static final String QUESTION_FILE_NAME = "quiz.txt";

    private final String question;
    private final String answer;

    /**
     * Constructs a Question object with a specified question and answer.
     *
     * @param question question text
     * @param answer   answer text
     * @throws IllegalArgumentException if the question or answer is null or blank
     */
    public Question(final String question,
                    final String answer) {

        validateQA(question);
        validateQA(answer);

        this.question = question;
        this.answer = answer;
    }

    /**
     * Reads questions from a file and returns a list of Question objects.
     *
     * @return list of Question objects read from the file
     * @throws RuntimeException if an I/O error occurs while reading the file
     */
    public static List<Question> getQuestions() {
        final List<Question> questions;
        final Path questionPath;

        questions = new ArrayList<>();
        questionPath = Paths.get(QUESTION_FILE_NAME);

        try {
            final List<String> lines;
            final Stream<String> lineStream;

            lines = Files.readAllLines(questionPath);
            parseQuestionsToList(lines, questions);

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return questions;
    }

    // Parses a List of Strings into a List of Question objects.
    private static void parseQuestionsToList(final List<String> lines, final List<Question> questions) {
        getFilteredStream(lines)
                .map(str -> str.split("\\|"))
                .map(str -> new Question(str[0].trim(), str[1].trim()))
                .forEach(questions::add);
    }

    // Filters the stream for no null or blank inputs from the Stream<String>
    private static Stream<String> getFilteredStream(final List<String> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .filter(p -> !p.isBlank());
    }

    // Validates qa to not be null or blank
    private static void validateQA(final String qa) {
        if(qa == null || qa.isBlank()) {
            throw new IllegalArgumentException("Invalid question/answer. Input: " + qa);
        }
    }

}
