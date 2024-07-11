package com.example.sce.db.course;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CourseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "courses.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_COURSES = "courses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COURSE_NAME = "course_name";
    public static final String COLUMN_COURSE_FEE = "course_fee";
    public static final String COLUMN_BRANCHES = "branches";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_PUBLISHED_DATE = "published_date";
    public static final String COLUMN_REGISTRATION_CLOSE_DATE = "registration_close_date";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_MAX_PARTICIPANTS = "max_participants";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_COURSES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COURSE_NAME + " TEXT, " +
                    COLUMN_COURSE_FEE + " REAL, " +
                    COLUMN_BRANCHES + " TEXT, " +
                    COLUMN_DURATION + " INTEGER, " +
                    COLUMN_PUBLISHED_DATE + " TEXT, " +
                    COLUMN_REGISTRATION_CLOSE_DATE + " TEXT, " +
                    COLUMN_START_DATE + " TEXT, " +
                    COLUMN_MAX_PARTICIPANTS + " INTEGER" +
                    ")";

    public CourseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        onCreate(db);
    }
}
