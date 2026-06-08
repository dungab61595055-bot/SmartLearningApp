package com.smartlearning.app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.smartlearning.app.database.DatabaseHelper;
import com.smartlearning.app.model.Schedule;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private DatabaseHelper dbHelper;

    public ScheduleDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // Add schedule
    public long addSchedule(Schedule schedule) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_SCHEDULE_SUBJECT_ID, schedule.getSubjectId());
        values.put(DatabaseHelper.COL_SCHEDULE_USER_ID, schedule.getUserId());
        values.put(DatabaseHelper.COL_SCHEDULE_DATE, schedule.getDate());
        values.put(DatabaseHelper.COL_SCHEDULE_START_TIME, schedule.getStartTime());
        values.put(DatabaseHelper.COL_SCHEDULE_END_TIME, schedule.getEndTime());
        values.put(DatabaseHelper.COL_SCHEDULE_TITLE, schedule.getTitle());
        values.put(DatabaseHelper.COL_SCHEDULE_TYPE, schedule.getType());

        return db.insert(DatabaseHelper.TABLE_SCHEDULES, null, values);
    }

    // Get all schedules for user
    public List<Schedule> getAllSchedules(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Schedule> schedules = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SCHEDULES,
                null,
                DatabaseHelper.COL_SCHEDULE_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                DatabaseHelper.COL_SCHEDULE_DATE + " ASC"
        );

        while (cursor.moveToNext()) {
            Schedule schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_ID)));
            schedule.setSubjectId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_SUBJECT_ID)));
            schedule.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_USER_ID)));
            schedule.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_DATE)));
            schedule.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_START_TIME)));
            schedule.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_END_TIME)));
            schedule.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_TITLE)));
            schedule.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_TYPE)));
            schedule.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_CREATED_AT)));
            schedules.add(schedule);
        }
        cursor.close();
        return schedules;
    }

    // Get schedules by date
    public List<Schedule> getSchedulesByDate(int userId, String date) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Schedule> schedules = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SCHEDULES,
                null,
                DatabaseHelper.COL_SCHEDULE_USER_ID + "=? AND " + DatabaseHelper.COL_SCHEDULE_DATE + "=?",
                new String[]{String.valueOf(userId), date},
                null,
                null,
                DatabaseHelper.COL_SCHEDULE_START_TIME + " ASC"
        );

        while (cursor.moveToNext()) {
            Schedule schedule = new Schedule();
            schedule.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_ID)));
            schedule.setSubjectId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_SUBJECT_ID)));
            schedule.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_USER_ID)));
            schedule.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_DATE)));
            schedule.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_START_TIME)));
            schedule.setEndTime(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_END_TIME)));
            schedule.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_TITLE)));
            schedule.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_TYPE)));
            schedule.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SCHEDULE_CREATED_AT)));
            schedules.add(schedule);
        }
        cursor.close();
        return schedules;
    }

    // Update schedule
    public int updateSchedule(Schedule schedule) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_SCHEDULE_SUBJECT_ID, schedule.getSubjectId());
        values.put(DatabaseHelper.COL_SCHEDULE_DATE, schedule.getDate());
        values.put(DatabaseHelper.COL_SCHEDULE_START_TIME, schedule.getStartTime());
        values.put(DatabaseHelper.COL_SCHEDULE_END_TIME, schedule.getEndTime());
        values.put(DatabaseHelper.COL_SCHEDULE_TITLE, schedule.getTitle());
        values.put(DatabaseHelper.COL_SCHEDULE_TYPE, schedule.getType());

        return db.update(
                DatabaseHelper.TABLE_SCHEDULES,
                values,
                DatabaseHelper.COL_SCHEDULE_ID + "=?",
                new String[]{String.valueOf(schedule.getId())}
        );
    }

    // Delete schedule
    public int deleteSchedule(int scheduleId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                DatabaseHelper.TABLE_SCHEDULES,
                DatabaseHelper.COL_SCHEDULE_ID + "=?",
                new String[]{String.valueOf(scheduleId)}
        );
    }
}
