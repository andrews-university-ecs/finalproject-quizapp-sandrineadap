package edu.andrews.cptr252.sandrine.quizapp;
import android.content.Context;
import java.util.ArrayList;
import java.util.UUID;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.andrews.cptr252.sandrine.quizapp.database.QuestionCursorWrapper;
import edu.andrews.cptr252.sandrine.quizapp.database.QuestionDbHelper;
import edu.andrews.cptr252.sandrine.quizapp.database.QuestionDbSchema.QuestionTable;

/**
 * Manage list of questions.
 * Singleton class; only one instance of this class may be created
 */
public class QuestionList {
    /** Instance variable for QuestionList */
    private static QuestionList sOurInstance;

    /** SQLite DB where questions are stored */
    private SQLiteDatabase mDatabase;

    /**
     * Pack question information into a ContentValues object.
     * @param question to pack.
     * @return resulting ContentValues object
     */
    public static ContentValues getContentValues(Question question) {
        ContentValues values = new ContentValues();
        values.put(QuestionTable.Cols.UUID, question.getId().toString());
        values.put(QuestionTable.Cols.CONTENT, question.getContent());
        values.put(QuestionTable.Cols.ANSWER, question.getAnswer());
        return values;
    }

    /**
     * Build a query for Question DB.
     * @param whereClause defines the where clause of a SQL query
     * @param whereArgs defines where arguments for a SQL query
     * @return Object defining a SQL query
     */
    private QuestionCursorWrapper queryQuestions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                QuestionTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new QuestionCursorWrapper(cursor);
    }

    private Context mAppContext;

    /**
     * Return list of Questions
     * @return Array of question objects
     */
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        QuestionCursorWrapper cursor = queryQuestions(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                questions.add(cursor.getQuestion());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return questions;
    }

    /**
     * Return the Question with a given id.
     * @param id Unique id for the question
     * @return The question object or null if not found.
     */
    public Question getQuestion(UUID id) {
        QuestionCursorWrapper cursor = queryQuestions(QuestionTable.Cols.UUID + " = ? ",
                new String[] { id.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getQuestion();
        } finally {
            cursor.close();
        }
    }

    /**
     * Add a question to the list.
     * @param question is the question to add.
     */
    public void addQuestion(Question question) {
        ContentValues values = getContentValues(question);
        mDatabase.insert(QuestionTable.NAME, null, values);
    }

    /**
     * Update information for a given question.
     * @param question contains the latest information for the question.
     */
    public void updateQuestion(Question question) {
        String uuidString = question.getId().toString();
        ContentValues values = getContentValues(question);
        mDatabase.update(QuestionTable.NAME, values,
                QuestionTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
    }

    /** constructor */
    private QuestionList(Context appContext) {
        mAppContext = appContext.getApplicationContext();

        // Open DB file or create it if it does not already exist.
        // If the DB is older version, onUpgrade will be called.
        mDatabase = new QuestionDbHelper(mAppContext).getWritableDatabase();
    }

    public void deleteQuestion(Question question) {
        String uuidString = question.getId().toString();
        mDatabase.delete(QuestionTable.NAME,
                QuestionTable.Cols.UUID + " = ? ",
                new String[] { uuidString} );
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

}// end of QuestionList
