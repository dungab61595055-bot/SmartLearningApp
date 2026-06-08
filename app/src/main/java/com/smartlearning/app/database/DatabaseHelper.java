package com.smartlearning.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartLearning.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String TABLE_SCHEDULES = "schedules";
    public static final String TABLE_TASKS = "tasks";
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_ANSWERS = "answers";
    public static final String TABLE_QUIZ_RESULTS = "quiz_results";

    // User table columns
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_PHONE = "phone";
    public static final String COL_USER_CREATED_AT = "created_at";

    // Subject table columns
    public static final String COL_SUBJECT_ID = "id";
    public static final String COL_SUBJECT_USER_ID = "user_id";
    public static final String COL_SUBJECT_NAME = "name";
    public static final String COL_SUBJECT_DESCRIPTION = "description";
    public static final String COL_SUBJECT_COLOR = "color";
    public static final String COL_SUBJECT_CREATED_AT = "created_at";

    // Schedule table columns
    public static final String COL_SCHEDULE_ID = "id";
    public static final String COL_SCHEDULE_SUBJECT_ID = "subject_id";
    public static final String COL_SCHEDULE_USER_ID = "user_id";
    public static final String COL_SCHEDULE_DATE = "date";
    public static final String COL_SCHEDULE_START_TIME = "start_time";
    public static final String COL_SCHEDULE_END_TIME = "end_time";
    public static final String COL_SCHEDULE_TITLE = "title";
    public static final String COL_SCHEDULE_TYPE = "type";
    public static final String COL_SCHEDULE_CREATED_AT = "created_at";

    // Task table columns
    public static final String COL_TASK_ID = "id";
    public static final String COL_TASK_SUBJECT_ID = "subject_id";
    public static final String COL_TASK_USER_ID = "user_id";
    public static final String COL_TASK_TITLE = "title";
    public static final String COL_TASK_DESCRIPTION = "description";
    public static final String COL_TASK_DUE_DATE = "due_date";
    public static final String COL_TASK_IS_COMPLETED = "is_completed";
    public static final String COL_TASK_COMPLETED_DATE = "completed_date";
    public static final String COL_TASK_CREATED_AT = "created_at";

    // Quiz table columns
    public static final String COL_QUIZ_ID = "id";
    public static final String COL_QUIZ_SUBJECT_ID = "subject_id";
    public static final String COL_QUIZ_USER_ID = "user_id";
    public static final String COL_QUIZ_TITLE = "title";
    public static final String COL_QUIZ_DESCRIPTION = "description";
    public static final String COL_QUIZ_TOTAL_QUESTIONS = "total_questions";
    public static final String COL_QUIZ_CREATED_AT = "created_at";

    // Question table columns
    public static final String COL_QUESTION_ID = "id";
    public static final String COL_QUESTION_QUIZ_ID = "quiz_id";
    public static final String COL_QUESTION_CONTENT = "content";
    public static final String COL_QUESTION_OPTION_A = "option_a";
    public static final String COL_QUESTION_OPTION_B = "option_b";
    public static final String COL_QUESTION_OPTION_C = "option_c";
    public static final String COL_QUESTION_OPTION_D = "option_d";
    public static final String COL_QUESTION_CORRECT_ANSWER = "correct_answer";

    // Answer table columns
    public static final String COL_ANSWER_ID = "id";
    public static final String COL_ANSWER_QUESTION_ID = "question_id";
    public static final String COL_ANSWER_SELECTED = "selected_answer";
    public static final String COL_ANSWER_IS_CORRECT = "is_correct";

    // Quiz Result table columns
    public static final String COL_RESULT_ID = "id";
    public static final String COL_RESULT_QUIZ_ID = "quiz_id";
    public static final String COL_RESULT_USER_ID = "user_id";
    public static final String COL_RESULT_SCORE = "score";
    public static final String COL_RESULT_TOTAL_QUESTIONS = "total_questions";
    public static final String COL_RESULT_CORRECT_ANSWERS = "correct_answers";
    public static final String COL_RESULT_COMPLETED_AT = "completed_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_NAME + " TEXT NOT NULL," +
                COL_USER_EMAIL + " TEXT UNIQUE NOT NULL," +
                COL_USER_PASSWORD + " TEXT NOT NULL," +
                COL_USER_PHONE + " TEXT," +
                COL_USER_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Subjects table
        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "(" +
                COL_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_SUBJECT_USER_ID + " INTEGER NOT NULL," +
                COL_SUBJECT_NAME + " TEXT NOT NULL," +
                COL_SUBJECT_DESCRIPTION + " TEXT," +
                COL_SUBJECT_COLOR + " TEXT," +
                COL_SUBJECT_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(" + COL_SUBJECT_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + ")" +
                ")";
        db.execSQL(CREATE_SUBJECTS_TABLE);

        // Create Schedules table
        String CREATE_SCHEDULES_TABLE = "CREATE TABLE " + TABLE_SCHEDULES + "(" +
                COL_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_SCHEDULE_SUBJECT_ID + " INTEGER NOT NULL," +
                COL_SCHEDULE_USER_ID + " INTEGER NOT NULL," +
                COL_SCHEDULE_DATE + " DATE NOT NULL," +
                COL_SCHEDULE_START_TIME + " TIME NOT NULL," +
                COL_SCHEDULE_END_TIME + " TIME NOT NULL," +
                COL_SCHEDULE_TITLE + " TEXT NOT NULL," +
                COL_SCHEDULE_TYPE + " TEXT," +
                COL_SCHEDULE_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(" + COL_SCHEDULE_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECTS + "(" + COL_SUBJECT_ID + ")," +
                "FOREIGN KEY(" + COL_SCHEDULE_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + ")" +
                ")";
        db.execSQL(CREATE_SCHEDULES_TABLE);

        // Create Tasks table
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" +
                COL_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TASK_SUBJECT_ID + " INTEGER NOT NULL," +
                COL_TASK_USER_ID + " INTEGER NOT NULL," +
                COL_TASK_TITLE + " TEXT NOT NULL," +
                COL_TASK_DESCRIPTION + " TEXT," +
                COL_TASK_DUE_DATE + " DATE," +
                COL_TASK_IS_COMPLETED + " INTEGER DEFAULT 0," +
                COL_TASK_COMPLETED_DATE + " DATETIME," +
                COL_TASK_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(" + COL_TASK_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECTS + "(" + COL_SUBJECT_ID + ")," +
                "FOREIGN KEY(" + COL_TASK_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + ")" +
                ")";
        db.execSQL(CREATE_TASKS_TABLE);

        // Create Quizzes table
        String CREATE_QUIZZES_TABLE = "CREATE TABLE " + TABLE_QUIZZES + "(" +
                COL_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_QUIZ_SUBJECT_ID + " INTEGER NOT NULL," +
                COL_QUIZ_USER_ID + " INTEGER NOT NULL," +
                COL_QUIZ_TITLE + " TEXT NOT NULL," +
                COL_QUIZ_DESCRIPTION + " TEXT," +
                COL_QUIZ_TOTAL_QUESTIONS + " INTEGER," +
                COL_QUIZ_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(" + COL_QUIZ_SUBJECT_ID + ") REFERENCES " + TABLE_SUBJECTS + "(" + COL_SUBJECT_ID + ")," +
                "FOREIGN KEY(" + COL_QUIZ_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + ")" +
                ")";
        db.execSQL(CREATE_QUIZZES_TABLE);

        // Create Questions table
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "(" +
                COL_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_QUESTION_QUIZ_ID + " INTEGER NOT NULL," +
                COL_QUESTION_CONTENT + " TEXT NOT NULL," +
                COL_QUESTION_OPTION_A + " TEXT NOT NULL," +
                COL_QUESTION_OPTION_B + " TEXT NOT NULL," +
                COL_QUESTION_OPTION_C + " TEXT NOT NULL," +
                COL_QUESTION_OPTION_D + " TEXT NOT NULL," +
                COL_QUESTION_CORRECT_ANSWER + " TEXT NOT NULL," +
                "FOREIGN KEY(" + COL_QUESTION_QUIZ_ID + ") REFERENCES " + TABLE_QUIZZES + "(" + COL_QUIZ_ID + ")" +
                ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        // Create Answers table
        String CREATE_ANSWERS_TABLE = "CREATE TABLE " + TABLE_ANSWERS + "(" +
                COL_ANSWER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ANSWER_QUESTION_ID + " INTEGER NOT NULL," +
                COL_ANSWER_SELECTED + " TEXT," +
                COL_ANSWER_IS_CORRECT + " INTEGER," +
                "FOREIGN KEY(" + COL_ANSWER_QUESTION_ID + ") REFERENCES " + TABLE_QUESTIONS + "(" + COL_QUESTION_ID + ")" +
                ")";
        db.execSQL(CREATE_ANSWERS_TABLE);

        // Create Quiz Results table
        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_QUIZ_RESULTS + "(" +
                COL_RESULT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_RESULT_QUIZ_ID + " INTEGER NOT NULL," +
                COL_RESULT_USER_ID + " INTEGER NOT NULL," +
                COL_RESULT_SCORE + " REAL," +
                COL_RESULT_TOTAL_QUESTIONS + " INTEGER," +
                COL_RESULT_CORRECT_ANSWERS + " INTEGER," +
                COL_RESULT_COMPLETED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(" + COL_RESULT_QUIZ_ID + ") REFERENCES " + TABLE_QUIZZES + "(" + COL_QUIZ_ID + ")," +
                "FOREIGN KEY(" + COL_RESULT_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + ")" +
                ")";
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
