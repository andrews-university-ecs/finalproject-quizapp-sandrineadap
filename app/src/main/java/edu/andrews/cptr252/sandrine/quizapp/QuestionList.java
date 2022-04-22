package edu.andrews.cptr252.sandrine.quizapp;
import android.content.Context;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Manage list of questions.
 * Singleton class; only one instance of this class may be created
 */
public class QuestionList {
    /** Instance variable for QuestionList */
    private static QuestionList sOurInstance;

    /** List of Questions */
    private ArrayList<Question> mQuestions;

    private Context mAppContext;

    /** constructor */
    private QuestionList(Context appContext) {
        mAppContext = appContext;
        mQuestions = new ArrayList<>();
        // for now, create a list of 10 dummy questions
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setContent("Question #" + i);
            // every other answer is true
            question.setAnswer(i % 2 == 0);
            mQuestions.add(question);
        }
    }

    /**
     * Return the one and only instance of questions list.
     * Create instance if it does not exist
     * @param c - Application context
     * @return reference to question list
     */
    public static QuestionList getInstance(Context c) {
        if (sOurInstance == null) {
            sOurInstance = new QuestionList(c.getApplicationContext());
        }
        return sOurInstance;
    }

    /**
     * Return list of Questions
     * @return Array of question objects
     */
    public ArrayList<Question> getQuestions() { return mQuestions; }

    /**
     * Return the Question with a given id.
     * @param id Unique id for the question
     * @return The question object or null if not found.
     */
    public Question getQuestion(UUID id) {
        for (Question question : mQuestions) {
            if (question.getId().equals(id))
                return question;
        }
        return null;
    }
}
