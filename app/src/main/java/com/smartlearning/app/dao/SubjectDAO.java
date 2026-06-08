package com.smartlearning.app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Subject;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private DatabaseHelper dbHelper;

    public SubjectDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Add subject
    public long addSubject(Subject subject) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_SUBJECT_USER_ID, subject.getUserId());
        values.put(DatabaseHelper.COL_SUBJECT_NAME, subject.getName());
        values.put(DatabaseHelper.COL_SUBJECT_DESCRIPTION, subject.getDescription());
        values.put(DatabaseHelper.COL_SUBJECT_COLOR, subject.getColor());

        return db.insert(DatabaseHelper.TABLE_SUBJECTS, null, values);
    }

    // Get all subjects for user
    public List<Subject> getAllSubjects(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Subject> subjects = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SUBJECTS,
                null,
                DatabaseHelper.COL_SUBJECT_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                DatabaseHelper.COL_SUBJECT_CREATED_AT + " DESC"
        );

        while (cursor.moveToNext()) {
            Subject subject = new Subject(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_COLOR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_CREATED_AT))
            );
            subjects.add(subject);
        }
        cursor.close();
        return subjects;
    }

    // Get subject by ID
    public Subject getSubjectById(int subjectId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SUBJECTS,
                null,
                DatabaseHelper.COL_SUBJECT_ID + "=?",
                new String[]{String.valueOf(subjectId)},
                null,
                null,
                null
        );

        Subject subject = null;
        if (cursor.moveToFirst()) {
            subject = new Subject(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_COLOR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_CREATED_AT))
            );
        }
        cursor.close();
        return subject;
    }

    // Update subject
    public int updateSubject(Subject subject) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_SUBJECT_NAME, subject.getName());
        values.put(DatabaseHelper.COL_SUBJECT_DESCRIPTION, subject.getDescription());
        values.put(DatabaseHelper.COL_SUBJECT_COLOR, subject.getColor());

        return db.update(
                DatabaseHelper.TABLE_SUBJECTS,
                values,
                DatabaseHelper.COL_SUBJECT_ID + "=?",
                new String[]{String.valueOf(subject.getId())}
        );
    }

    // Delete subject
    public int deleteSubject(int subjectId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseHelper.TABLE_SUBJECTS,
                DatabaseHelper.COL_SUBJECT_ID + "=?",
                new String[]{String.valueOf(subjectId)}
        );
    }

    // Search subjects
    public List<Subject> searchSubjects(int userId, String keyword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Subject> subjects = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SUBJECTS,
                null,
                DatabaseHelper.COL_SUBJECT_USER_ID + "=? AND " + DatabaseHelper.COL_SUBJECT_NAME + " LIKE ?",
                new String[]{String.valueOf(userId), "%" + keyword + "%"},
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Subject subject = new Subject(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_COLOR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SUBJECT_CREATED_AT))
            );
            subjects.add(subject);
        }
        cursor.close();
        return subjects;
    }
}
