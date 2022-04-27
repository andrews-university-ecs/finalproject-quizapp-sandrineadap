package edu.andrews.cptr252.sandrine.quizapp;
import java.util.UUID;

/**
 * Manage information for a specified question
 */
public class Question {
    /** Unique Id for each question */
    private UUID mId;

    /** Content of question to ask */
    private String mContent;

    /** Correct answer (true or false questions) */
    private Boolean mAnswer;

    /**
     * Create an initialize question from UUID.
     * If no UUID is given, create one.
     */
    public Question(UUID id) {
        mId = id;
    }

    public Question() {
        // Generate unique identifier for new question
        // mId = UUID.randomUUID();
        this(UUID.randomUUID());
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
    public String getContent() {
        return mContent;
    }

    /**
     * provide new question to be asked
     * @param content of New question
     */
    public void setContent(String content) {
        mContent = content;
    }

    public Boolean getAnswer() { return mAnswer; }
    public void setAnswer(boolean answer) { mAnswer = answer; }
}
