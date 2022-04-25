package edu.andrews.cptr252.sandrine.quizapp.database;

/**
 *  Define schema for question database
 *
 */
public class QuestionDbSchema {
    public static final class QuestionTable {
        public static final String NAME = "questions";
        /**
         * DB column names.
         */
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String CONTENT = "content";
            public static final String ANSWER = "answer";
        }
    }

}
