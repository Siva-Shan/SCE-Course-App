package com.example.sce.db.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.sce.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    private SQLiteDatabase database;
    private CourseHelper dbHelper;

    private String[] allColumns = {
            CourseHelper.COLUMN_ID,
            CourseHelper.COLUMN_COURSE_NAME,
            CourseHelper.COLUMN_COURSE_FEE,
            CourseHelper.COLUMN_BRANCHES,
            CourseHelper.COLUMN_DURATION,
            CourseHelper.COLUMN_PUBLISHED_DATE,
            CourseHelper.COLUMN_REGISTRATION_CLOSE_DATE,
            CourseHelper.COLUMN_START_DATE,
            CourseHelper.COLUMN_MAX_PARTICIPANTS
    };
    public CourseDao(Context context) {
        dbHelper = new CourseHelper(context);
        open();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseHelper.COLUMN_COURSE_NAME, course.getCourseName());
        values.put(CourseHelper.COLUMN_COURSE_FEE, course.getCourseFee());
        values.put(CourseHelper.COLUMN_BRANCHES, String.join(",", course.getBranches()));
        values.put(CourseHelper.COLUMN_DURATION, course.getDuration());
        values.put(CourseHelper.COLUMN_PUBLISHED_DATE, course.getPublishedDate());
        values.put(CourseHelper.COLUMN_REGISTRATION_CLOSE_DATE, course.getRegistrationCloseDate());
        values.put(CourseHelper.COLUMN_START_DATE, course.getStartDate());
        values.put(CourseHelper.COLUMN_MAX_PARTICIPANTS, course.getMaxParticipants());

        database.insert(CourseHelper.TABLE_COURSES, null, values);
    }

    public Course getCourse(long id) {
        Cursor cursor = database.query(CourseHelper.TABLE_COURSES, allColumns, CourseHelper.COLUMN_ID + " = " + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Course course = cursorToCourse(cursor);
            cursor.close();
            return course;
        }
        return null;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        Cursor cursor = database.query(CourseHelper.TABLE_COURSES, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Course course = cursorToCourse(cursor);
            courses.add(course);
            cursor.moveToNext();
        }
        cursor.close();
        return courses;
    }

    public int updateCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(CourseHelper.COLUMN_COURSE_NAME, course.getCourseName());
        values.put(CourseHelper.COLUMN_COURSE_FEE, course.getCourseFee());
        values.put(CourseHelper.COLUMN_BRANCHES, String.join(",", course.getBranches()));
        values.put(CourseHelper.COLUMN_DURATION, course.getDuration());
        values.put(CourseHelper.COLUMN_PUBLISHED_DATE, course.getPublishedDate());
        values.put(CourseHelper.COLUMN_REGISTRATION_CLOSE_DATE, course.getRegistrationCloseDate());
        values.put(CourseHelper.COLUMN_START_DATE, course.getStartDate());
        values.put(CourseHelper.COLUMN_MAX_PARTICIPANTS, course.getMaxParticipants());

        return database.update(CourseHelper.TABLE_COURSES, values, CourseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(course.getId())});
    }

    public void deleteCourse(long id) {
        database.delete(CourseHelper.TABLE_COURSES, CourseHelper.COLUMN_ID + " = " + id, null);
    }

    private Course cursorToCourse(Cursor cursor) {
        Course course = new Course();
        course.setId(cursor.getLong(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_ID)));
        course.setCourseName(cursor.getString(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_COURSE_NAME)));
        course.setCourseFee(cursor.getDouble(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_COURSE_FEE)));
        course.setBranches(cursor.getString(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_BRANCHES)).split(","));
        course.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_DURATION)));
        course.setPublishedDate(cursor.getString(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_PUBLISHED_DATE)));
        course.setRegistrationCloseDate(cursor.getString(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_REGISTRATION_CLOSE_DATE)));
        course.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_START_DATE)));
        course.setMaxParticipants(cursor.getInt(cursor.getColumnIndexOrThrow(CourseHelper.COLUMN_MAX_PARTICIPANTS)));
        return course;
    }
}
