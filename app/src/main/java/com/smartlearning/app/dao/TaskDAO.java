package com.smartlearning.app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private DatabaseHelper dbHelper;

    public TaskDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Add task
    public long addTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TASK_SUBJECT_ID, task.getSubjectId());
        values.put(DatabaseHelper.COL_TASK_USER_ID, task.getUserId());
        values.put(DatabaseHelper.COL_TASK_TITLE, task.getTitle());
        values.put(DatabaseHelper.COL_TASK_DESCRIPTION, task.getDescription());
        values.put(DatabaseHelper.COL_TASK_DUE_DATE, task.getDueDate());
        values.put(DatabaseHelper.COL_TASK_IS_COMPLETED, task.isCompleted() ? 1 : 0);

        return db.insert(DatabaseHelper.TABLE_TASKS, null, values);
    }

    // Get all tasks for user
    public List<Task> getAllTasks(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TASKS,
                null,
                DatabaseHelper.COL_TASK_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                DatabaseHelper.COL_TASK_DUE_DATE + " ASC"
        );

        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_ID)));
            task.setSubjectId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_SUBJECT_ID)));
            task.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_USER_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_TITLE)));
            task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_DESCRIPTION)));
            task.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_DUE_DATE)));
            task.setCompleted(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_IS_COMPLETED)) == 1);
            task.setCompletedDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_COMPLETED_DATE)));
            task.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TASK_CREATED_AT)));
            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    // Get completed tasks count
    public int getCompletedTasksCount(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TASKS,
                null,
                DatabaseHelper.COL_TASK_USER_ID + "=? AND " + DatabaseHelper.COL_TASK_IS_COMPLETED + "=1",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Get total tasks count
    public int getTotalTasksCount(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_TASKS,
                null,
                DatabaseHelper.COL_TASK_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Update task
    public int updateTask(Task task) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TASK_SUBJECT_ID, task.getSubjectId());
        values.put(DatabaseHelper.COL_TASK_TITLE, task.getTitle());
        values.put(DatabaseHelper.COL_TASK_DESCRIPTION, task.getDescription());
        values.put(DatabaseHelper.COL_TASK_DUE_DATE, task.getDueDate());
        values.put(DatabaseHelper.COL_TASK_IS_COMPLETED, task.isCompleted() ? 1 : 0);
        values.put(DatabaseHelper.COL_TASK_COMPLETED_DATE, task.getCompletedDate());

        return db.update(
                DatabaseHelper.TABLE_TASKS,
                values,
                DatabaseHelper.COL_TASK_ID + "=?",
                new String[]{String.valueOf(task.getId())}
        );
    }

    // Delete task
    public int deleteTask(int taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseHelper.TABLE_TASKS,
                DatabaseHelper.COL_TASK_ID + "=?",
                new String[]{String.valueOf(taskId)}
        );
    }
}
