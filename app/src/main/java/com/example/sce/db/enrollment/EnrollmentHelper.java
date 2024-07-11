package com.example.sce.db.enrollment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnrollmentHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "enrollments.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_ENROLLMENT = "enrollment";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PURCHASE_CODE = "purchase_code";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_COURSE_ID = "course_id";
    public static final String COLUMN_BRANCH = "branch";
    public static final String COLUMN_PURCHASED_DATE = "purchased_date";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ENROLLMENT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PURCHASE_CODE + " TEXT, " +
                    COLUMN_USER_ID + " INTEGER, " +
                    COLUMN_COURSE_ID + " INTEGER, " +
                    COLUMN_BRANCH + " TEXT, " +
                    COLUMN_PURCHASED_DATE + " TEXT" +
                    ");";

    public EnrollmentHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENT);
        onCreate(db);
    }
}

