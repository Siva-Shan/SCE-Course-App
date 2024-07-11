package com.example.sce.db.branch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BranchHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "branchDB";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_BRANCHES = "branches";

    public static final String COLUMN_BRANCH_CODE = "branchCode";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_BRANCH_NAME = "branchName";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";

    private static final String CREATE_TABLE_BRANCHES =
            "CREATE TABLE " + TABLE_BRANCHES + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BRANCH_CODE + " TEXT, " +
                    COLUMN_BRANCH_NAME + " TEXT, " +
                    COLUMN_LONGITUDE + " REAL, " +
                    COLUMN_LATITUDE + " REAL)";

    public BranchHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BRANCHES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCHES);
        onCreate(db);
    }

}
