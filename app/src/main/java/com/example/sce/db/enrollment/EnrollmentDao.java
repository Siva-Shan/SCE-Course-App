package com.example.sce.db.enrollment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sce.model.Enrollment;
import com.example.sce.model.Purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollmentDao {
    private SQLiteDatabase db;
    private EnrollmentHelper dbHelper;

    public EnrollmentDao(Context context) {
        dbHelper = new EnrollmentHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertEnrollment(Enrollment enrollment) {
        ContentValues values = new ContentValues();
        values.put(EnrollmentHelper.COLUMN_PURCHASE_CODE, enrollment.getPurchaseCode());
        values.put(EnrollmentHelper.COLUMN_USER_ID, enrollment.getUserId());
        values.put(EnrollmentHelper.COLUMN_COURSE_ID, enrollment.getCourseId());
        values.put(EnrollmentHelper.COLUMN_BRANCH, enrollment.getBranch());
        values.put(EnrollmentHelper.COLUMN_PURCHASED_DATE, enrollment.getPurchasedDate());
        db.insert(EnrollmentHelper.TABLE_ENROLLMENT, null, values);
    }

    public List<Purchase> getAllPurchases(int userId) {
        Map<String, Purchase> purchaseMap = new HashMap<>();

        Cursor cursor = db.query(EnrollmentHelper.TABLE_ENROLLMENT,
                null,
                EnrollmentHelper.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String purchaseCode = cursor.getString(cursor.getColumnIndex(EnrollmentHelper.COLUMN_PURCHASE_CODE));
                int courseId = cursor.getInt(cursor.getColumnIndex(EnrollmentHelper.COLUMN_COURSE_ID));
                String branch = cursor.getString(cursor.getColumnIndex(EnrollmentHelper.COLUMN_BRANCH));
                String purchasedDate = cursor.getString(cursor.getColumnIndex(EnrollmentHelper.COLUMN_PURCHASED_DATE));

                Enrollment enrollment = new Enrollment(purchaseCode, userId, courseId, branch, purchasedDate);

                Purchase purchase = purchaseMap.get(purchaseCode);
                if (purchase == null) {
                    purchase = new Purchase();
                    purchase.setPurchaseCode(purchaseCode);
                    purchase.setTotalPrice(0.0); // This can be updated to actual total price if available
                    purchaseMap.put(purchaseCode, purchase);
                }
                purchase.addEnrollment(enrollment);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return new ArrayList<>(purchaseMap.values());
    }

    public Purchase getPurchaseById(String purchaseCode) {
        Purchase purchase = null;

        Cursor cursor = db.query(EnrollmentHelper.TABLE_ENROLLMENT,
                null, EnrollmentHelper.COLUMN_PURCHASE_CODE + " = ?",
                new String[]{purchaseCode}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            purchase = new Purchase();
            purchase.setPurchaseCode(purchaseCode);
            purchase.setEnrollments(new ArrayList<>());

            do {
                int userId = cursor.getInt(cursor.getColumnIndex(EnrollmentHelper.COLUMN_USER_ID));
                int courseId = cursor.getInt(cursor.getColumnIndex(EnrollmentHelper.COLUMN_COURSE_ID));
                String branch = cursor.getString(cursor.getColumnIndex(EnrollmentHelper.COLUMN_BRANCH));
                String purchasedDate = cursor.getString(cursor.getColumnIndex(EnrollmentHelper.COLUMN_PURCHASED_DATE));

                Enrollment enrollment = new Enrollment(purchaseCode, userId, courseId, branch, purchasedDate);
                purchase.addEnrollment(enrollment);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return purchase;
    }

    public void deletePurchase(String purchaseCode) {
        db.delete(EnrollmentHelper.TABLE_ENROLLMENT,
                EnrollmentHelper.COLUMN_PURCHASE_CODE + " = ?",
                new String[]{purchaseCode});
    }
}
