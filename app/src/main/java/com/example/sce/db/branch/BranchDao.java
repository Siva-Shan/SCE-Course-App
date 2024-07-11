package com.example.sce.db.branch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sce.model.Branch;

import java.util.ArrayList;
import java.util.List;

public class BranchDao {
    private SQLiteDatabase database;
    private BranchHelper dbHelper;

    public BranchDao(Context context) {
        dbHelper = new BranchHelper(context);
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    // Create
    public void addBranch(Branch branch) {
        ContentValues values = new ContentValues();
        values.put(BranchHelper.COLUMN_BRANCH_CODE, branch.getBranchCode());
        values.put(BranchHelper.COLUMN_BRANCH_NAME, branch.getBranchName());
        values.put(BranchHelper.COLUMN_LONGITUDE, branch.getLongitude());
        values.put(BranchHelper.COLUMN_LATITUDE, branch.getLatitude());

        database.insert(BranchHelper.TABLE_BRANCHES, null, values);
        database.close();
    }

    // Read
    public Branch getBranch(String branchCode) {
        Cursor cursor = database.query(BranchHelper.TABLE_BRANCHES, new String[]{BranchHelper.COLUMN_BRANCH_CODE, BranchHelper.COLUMN_BRANCH_NAME, BranchHelper.COLUMN_LONGITUDE, BranchHelper.COLUMN_LATITUDE},
                BranchHelper.COLUMN_BRANCH_CODE + "=?", new String[]{branchCode}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Branch branch = new Branch(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getDouble(3)
        );

        cursor.close();
        return branch;
    }

    // Read all
    public List<Branch> getAllBranches() {
        List<Branch> branchList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + BranchHelper.TABLE_BRANCHES;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Branch branch = new Branch();
                branch.setId(Long.parseLong(cursor.getString(0)));
                branch.setBranchCode(cursor.getString(1));
                branch.setBranchName(cursor.getString(2));
                branch.setLongitude(cursor.getDouble(3));
                branch.setLatitude(cursor.getDouble(4));

                branchList.add(branch);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return branchList;
    }

    // Update
    public int updateBranch(Branch branch) {
        ContentValues values = new ContentValues();
        values.put(BranchHelper.COLUMN_BRANCH_NAME, branch.getBranchName());
        values.put(BranchHelper.COLUMN_BRANCH_CODE, branch.getBranchCode());
        values.put(BranchHelper.COLUMN_LONGITUDE, branch.getLongitude());
        values.put(BranchHelper.COLUMN_LATITUDE, branch.getLatitude());

        return database.update(BranchHelper.TABLE_BRANCHES, values, BranchHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(branch.getId())});
    }

    // Delete
    public void deleteBranch(String branchCode) {
        database.delete(BranchHelper.TABLE_BRANCHES, BranchHelper.COLUMN_BRANCH_CODE + " = ?",
                new String[]{branchCode});
        database.close();
    }

    // Delete all
    public void deleteAllBranches() {
        database.delete(BranchHelper.TABLE_BRANCHES, null, null);
        database.close();
    }

    // Get count
    public int getBranchCount() {
        String countQuery = "SELECT * FROM " + BranchHelper.TABLE_BRANCHES;

        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}
