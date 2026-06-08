package com.smartlearning.app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.QuizResult;
import java.util.ArrayList;
import java.util.List;

public class QuizResultDAO {
    private DatabaseHelper dbHelper;

    public QuizResultDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Add quiz result
    public long addQuizResult(QuizResult result) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_RESULT_QUIZ_ID, result.getQuizId());
        values.put(DatabaseHelper.COL_RESULT_USER_ID, result.getUserId());
        values.put(DatabaseHelper.COL_RESULT_SCORE, result.getScore());
        values.put(DatabaseHelper.COL_RESULT_TOTAL_QUESTIONS, result.getTotalQuestions());
        values.put(DatabaseHelper.COL_RESULT_CORRECT_ANSWERS, result.getCorrectAnswers());

        return db.insert(DatabaseHelper.TABLE_QUIZ_RESULTS, null, values);
    }

    // Get all results for user
    public List<QuizResult> getAllResults(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<QuizResult> results = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_QUIZ_RESULTS,
                null,
                DatabaseHelper.COL_RESULT_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                DatabaseHelper.COL_RESULT_COMPLETED_AT + " DESC"
        );

        while (cursor.moveToNext()) {
            QuizResult result = new QuizResult();
            result.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESULT_ID)));
            result.setQuizId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESULT_QUIZ_ID)));
            result.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESULT_USER_ID)));
            result.setScore(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESULT_SCORE)));
            result.setTotalQuestions(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESULT_TOTAL_QUESTIONS)));
            result.setCorrectAnswers(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESULT_CORRECT_ANSWERS)));
            result.setCompletedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RESULT_COMPLETED_AT)));
            results.add(result);
        }
        cursor.close();
        return results;
    }

    // Get average score for user
    public double getAverageScore(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT AVG(" + DatabaseHelper.COL_RESULT_SCORE + ") FROM " + DatabaseHelper.TABLE_QUIZ_RESULTS +
                        " WHERE " + DatabaseHelper.COL_RESULT_USER_ID + "=?",
                new String[]{String.valueOf(userId)}
        );

        double average = 0.0;
        if (cursor.moveToFirst()) {
            average = cursor.getDouble(0);
        }
        cursor.close();
        return average;
    }

    // Get total quizzes attempted
    public int getTotalQuizzesAttempted(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_QUIZ_RESULTS,
                null,
                DatabaseHelper.COL_RESULT_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
