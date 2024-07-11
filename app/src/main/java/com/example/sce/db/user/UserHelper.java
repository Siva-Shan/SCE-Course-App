package com.example.sce.db.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LIVING_CITY = "livingCity";
    public static final String COLUMN_DATE_OF_BIRTH = "dateOfBirth";
    public static final String COLUMN_NIC = "NIC";
    public static final String COLUMN_EMAIL_ADDRESS = "emailAddress";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
    public static final String COLUMN_PROFILE_PICTURE_PATH = "profilePicturePath";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USER + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_LIVING_CITY + " TEXT, " +
                    COLUMN_DATE_OF_BIRTH + " TEXT, " +
                    COLUMN_NIC + " TEXT, " +
                    COLUMN_EMAIL_ADDRESS + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_MOBILE_PHONE_NUMBER + " TEXT, " +
                    COLUMN_PROFILE_PICTURE_PATH + " TEXT" +
                    ");";

    public UserHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
