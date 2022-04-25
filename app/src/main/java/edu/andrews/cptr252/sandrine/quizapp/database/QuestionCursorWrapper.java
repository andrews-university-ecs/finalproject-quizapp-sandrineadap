package edu.andrews.cptr252.sandrine.quizapp.database;
import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.Date;
import java.util.UUID;
import edu.andrews.cptr252.sandrine.quizapp.Question;
import edu.andrews.cptr252.sandrine.quizapp.database.QuestionDbSchema.QuestionTable;

/**
 * Handle results from DB queries
 */
public class QuestionCursorWrapper extends CursorWrapper {
    public QuestionCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Extract question information from a query.
     * @return Question that resulted from the query.
     */

    public Question getQuestion() {
        String uuidString = getString(getColumnIndex(QuestionTable.Cols.UUID));
        String content = getString(getColumnIndex(QuestionTable.Cols.CONTENT));
        boolean answer = getInt(getColumnIndex(QuestionTable.Cols.ANSWER)) > 0;
        Question question = new Question(UUID.fromString(uuidString));
        question.setContent(content);
        question.setAnswer(answer);
        return question;
    }

}
