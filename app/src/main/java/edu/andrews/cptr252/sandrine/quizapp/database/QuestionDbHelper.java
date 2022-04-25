package edu.andrews.cptr252.sandrine.quizapp.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.andrews.cptr252.sandrine.quizapp.Question;
import edu.andrews.cptr252.sandrine.quizapp.database.QuestionDbSchema.QuestionTable;

/**
 * Methods for creating/upgrading Question DB.
 */
public class QuestionDbHelper extends SQLiteOpenHelper {
    /** Current DB version. Increment as DB structure changes */
    private static final int VERSION = 1;
    /** DB filename */
    private static final String DATABASE_NAME = "questionDb.db";

    public QuestionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * DB does not exist. Create it
     * @param db will contain the newly created database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // execute SQL command to create a new bug database
        db.execSQL("create table " + QuestionTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                QuestionTable.Cols.UUID + ", " +
                QuestionTable.Cols.CONTENT + ", " +
                QuestionTable.Cols.ANSWER +
                ")");
    }

    /**
     * Previous DB is older and needs to be upgraded to current version.
     * @param db is the database that needs to be upgraded
     * @param oldVersion is the version number of the DB.
     * @param newVersion is the version the DB should be upgraded to.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
