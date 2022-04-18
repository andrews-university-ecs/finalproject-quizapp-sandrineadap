package edu.andrews.cptr252.sandrine.quizapp;
import java.util.UUID;

/**
 * Manage information for a specified question
 */
public class Question {
    /** Unique Id for each question */
    private UUID mId;

    /** Question to ask */
    private String mQuestion;

    /** Correct answer (true or false questions) */
    private boolean mAnswer;

    /**
     * Create an initialize new question
     */
    public Question() {
        // Generate unique identifier for new bug
        mId = UUID.randomUUID();
    }

    /**
     * @return unique question id
     */
    public UUID getId() {
        return mId;
    }

    /**
     * @return question to be asked
     */
    public String getQuestion() {
        return mQuestion;
    }

    /**
     * provide new question to be asked
     * @param  question New question
     */
    public void setQuestion(String question) {
        mQuestion = question;
    }

    public boolean getAnswer() { return mAnswer; }
    public void setAnswer(boolean answer) { mAnswer = answer; }
}
