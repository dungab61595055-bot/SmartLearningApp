package com.smartlearning.app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Quiz;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private DatabaseHelper dbHelper;

    public QuizDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Add quiz
    public long addQuiz(Quiz quiz) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_QUIZ_SUBJECT_ID, quiz.getSubjectId());
        values.put(DatabaseHelper.COL_QUIZ_USER_ID, quiz.getUserId());
        values.put(DatabaseHelper.COL_QUIZ_TITLE, quiz.getTitle());
        values.put(DatabaseHelper.COL_QUIZ_DESCRIPTION, quiz.getDescription());
        values.put(DatabaseHelper.COL_QUIZ_TOTAL_QUESTIONS, quiz.getTotalQuestions());

        return db.insert(DatabaseHelper.TABLE_QUIZZES, null, values);
    }

    // Get all quizzes for user
    public List<Quiz> getAllQuizzes(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_QUIZZES,
                null,
                DatabaseHelper.COL_QUIZ_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                DatabaseHelper.COL_QUIZ_CREATED_AT + " DESC"
        );

        while (cursor.moveToNext()) {
            Quiz quiz = new Quiz();
            quiz.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_ID)));
            quiz.setSubjectId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_SUBJECT_ID)));
            quiz.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_USER_ID)));
            quiz.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_TITLE)));
            quiz.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_DESCRIPTION)));
            quiz.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_TOTAL_QUESTIONS)));
            quiz.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_CREATED_AT)));
            quizzes.add(quiz);
        }
        cursor.close();
        return quizzes;
    }

    // Get quiz by ID
    public Quiz getQuizById(int quizId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_QUIZZES,
                null,
                DatabaseHelper.COL_QUIZ_ID + "=?",
                new String[]{String.valueOf(quizId)},
                null,
                null,
                null
        );

        Quiz quiz = null;
        if (cursor.moveToFirst()) {
            quiz = new Quiz();
            quiz.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_ID)));
            quiz.setSubjectId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_SUBJECT_ID)));
            quiz.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_USER_ID)));
            quiz.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_TITLE)));
            quiz.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_DESCRIPTION)));
            quiz.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_TOTAL_QUESTIONS)));
            quiz.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUIZ_CREATED_AT)));
        }
        cursor.close();
        return quiz;
    }

    // Update quiz
    public int updateQuiz(Quiz quiz) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_QUIZ_TITLE, quiz.getTitle());
        values.put(DatabaseHelper.COL_QUIZ_DESCRIPTION, quiz.getDescription());
        values.put(DatabaseHelper.COL_QUIZ_TOTAL_QUESTIONS, quiz.getTotalQuestions());

        return db.update(
                DatabaseHelper.TABLE_QUIZZES,
                values,
                DatabaseHelper.COL_QUIZ_ID + "=?",
                new String[]{String.valueOf(quiz.getId())}
        );
    }

    // Delete quiz
    public int deleteQuiz(int quizId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseHelper.TABLE_QUIZZES,
                DatabaseHelper.COL_QUIZ_ID + "=?",
                new String[]{String.valueOf(quizId)}
        );
    }
}
