package ca.bcit.comp2522.lab9.bam;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private final String question;
    private final String answer;

    public Question(final String question,
                    final String answer) {

        validateQA(question);
        validateQA(answer);

        this.question = question;
        this.answer = answer;
    }

    public static List<Question> getQuestions() {
        final List<Question> questions;
        questions = new ArrayList<>();

        return questions;
    }

    private static void validateQA(final String qa) {
        if(qa == null || qa.isBlank()) {
            throw new IllegalArgumentException("Invalid question/answer. Input: " + qa);
        }
    }

}
