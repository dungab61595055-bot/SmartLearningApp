package com.smartlearning.app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.User;

public class UserDAO {
    private DatabaseHelper dbHelper;

    public UserDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Register user
    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_NAME, user.getName());
        values.put(DatabaseHelper.COL_USER_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COL_USER_PASSWORD, user.getPassword());
        values.put(DatabaseHelper.COL_USER_PHONE, user.getPhone());

        return db.insert(DatabaseHelper.TABLE_USERS, null, values);
    }

    // Login user
    public User getUserByEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null,
                DatabaseHelper.COL_USER_EMAIL + "=?",
                new String[]{email},
                null,
                null,
                null
        );

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_CREATED_AT))
            );
        }
        cursor.close();
        return user;
    }

    // Get user by ID
    public User getUserById(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null,
                DatabaseHelper.COL_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_CREATED_AT))
            );
        }
        cursor.close();
        return user;
    }

    // Update user
    public int updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_NAME, user.getName());
        values.put(DatabaseHelper.COL_USER_PHONE, user.getPhone());

        return db.update(
                DatabaseHelper.TABLE_USERS,
                values,
                DatabaseHelper.COL_USER_ID + "=?",
                new String[]{String.valueOf(user.getId())}
        );
    }

    // Change password
    public int updatePassword(int userId, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_PASSWORD, newPassword);

        return db.update(
                DatabaseHelper.TABLE_USERS,
                values,
                DatabaseHelper.COL_USER_ID + "=?",
                new String[]{String.valueOf(userId)}
        );
    }

    // Check if email exists
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USERS,
                null,
                DatabaseHelper.COL_USER_EMAIL + "=?",
                new String[]{email},
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
