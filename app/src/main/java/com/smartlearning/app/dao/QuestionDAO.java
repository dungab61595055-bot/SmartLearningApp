package com.smartlearning.app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Question;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private DatabaseHelper dbHelper;

    public QuestionDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Add question
    public long addQuestion(Question question) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_QUESTION_QUIZ_ID, question.getQuizId());
        values.put(DatabaseHelper.COL_QUESTION_CONTENT, question.getContent());
        values.put(DatabaseHelper.COL_QUESTION_OPTION_A, question.getOptionA());
        values.put(DatabaseHelper.COL_QUESTION_OPTION_B, question.getOptionB());
        values.put(DatabaseHelper.COL_QUESTION_OPTION_C, question.getOptionC());
        values.put(DatabaseHelper.COL_QUESTION_OPTION_D, question.getOptionD());
        values.put(DatabaseHelper.COL_QUESTION_CORRECT_ANSWER, question.getCorrectAnswer());

        return db.insert(DatabaseHelper.TABLE_QUESTIONS, null, values);
    }

    // Get all questions for quiz
    public List<Question> getQuestionsByQuiz(int quizId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Question> questions = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_QUESTIONS,
                null,
                DatabaseHelper.COL_QUESTION_QUIZ_ID + "=?",
                new String[]{String.valueOf(quizId)},
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Question question = new Question();
            question.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_ID)));
            question.setQuizId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_QUIZ_ID)));
            question.setContent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_CONTENT)));
            question.setOptionA(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_A)));
            question.setOptionB(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_B)));
            question.setOptionC(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_C)));
            question.setOptionD(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_D)));
            question.setCorrectAnswer(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_CORRECT_ANSWER)));
            questions.add(question);
        }
        cursor.close();
        return questions;
    }

    // Get question by ID
    public Question getQuestionById(int questionId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_QUESTIONS,
                null,
                DatabaseHelper.COL_QUESTION_ID + "=?",
                new String[]{String.valueOf(questionId)},
                null,
                null,
                null
        );

        Question question = null;
        if (cursor.moveToFirst()) {
            question = new Question();
            question.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_ID)));
            question.setQuizId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_QUIZ_ID)));
            question.setContent(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_CONTENT)));
            question.setOptionA(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_A)));
            question.setOptionB(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_B)));
            question.setOptionC(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_C)));
            question.setOptionD(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_OPTION_D)));
            question.setCorrectAnswer(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUESTION_CORRECT_ANSWER)));
        }
        cursor.close();
        return question;
    }

    // Delete question
    public int deleteQuestion(int questionId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseHelper.TABLE_QUESTIONS,
                DatabaseHelper.COL_QUESTION_ID + "=?",
                new String[]{String.valueOf(questionId)}
        );
    }
}
