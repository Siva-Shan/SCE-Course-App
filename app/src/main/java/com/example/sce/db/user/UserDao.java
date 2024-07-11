package com.example.sce.db.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sce.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private SQLiteDatabase database;
    private UserHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new UserHelper(context);
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long register(User user) {

        String password = user.getPassword();
        if (password == null) {
            Log.e("UserHelper", "Password is null");
        } else {
            Log.d("UserHelper", "Password: " + password);
        }

        ContentValues values = new ContentValues();
        values.put(UserHelper.COLUMN_NAME, user.getName());
        values.put(UserHelper.COLUMN_PASSWORD, user.getPassword());
        values.put(UserHelper.COLUMN_ADDRESS, user.getAddress());
        values.put(UserHelper.COLUMN_LIVING_CITY, user.getLivingCity());
        values.put(UserHelper.COLUMN_DATE_OF_BIRTH, user.getDateOfBirth());
        values.put(UserHelper.COLUMN_NIC, user.getNIC());
        values.put(UserHelper.COLUMN_EMAIL_ADDRESS, user.getEmailAddress());
        values.put(UserHelper.COLUMN_GENDER, user.getGender());
        values.put(UserHelper.COLUMN_MOBILE_PHONE_NUMBER, user.getMobilePhoneNumber());

        return database.insert(UserHelper.TABLE_USER, null, values);
    }

    public boolean loginUser(String email, String password) {
        Cursor cursor = null;
        try {
            cursor = database.query(UserHelper.TABLE_USER,
                    null,
                    UserHelper.COLUMN_EMAIL_ADDRESS + " = ?",
                    new String[]{String.valueOf(email)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                User user = cursorToUser(cursor);
                // Check password (assuming plain text here, but consider hashing in production)
                return user.getPassword().equals(password);
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public long getUserID(String email) {
        Cursor cursor = database.query(UserHelper.TABLE_USER,
                null,
                UserHelper.COLUMN_EMAIL_ADDRESS + " = ?",
                new String[]{String.valueOf(email)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            User user = cursorToUser(cursor);
            cursor.close();
            return user.getId();
        }
        return -1;
    }

    public User getUser(long id) {
        Cursor cursor = database.query(UserHelper.TABLE_USER,
                null,
                UserHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            User user = cursorToUser(cursor);
            cursor.close();
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Cursor cursor = database.query(UserHelper.TABLE_USER,
                null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                users.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return users;
    }

    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserHelper.COLUMN_NAME, user.getName());
        values.put(UserHelper.COLUMN_ADDRESS, user.getAddress());
        values.put(UserHelper.COLUMN_LIVING_CITY, user.getLivingCity());
        values.put(UserHelper.COLUMN_DATE_OF_BIRTH, user.getDateOfBirth());
        values.put(UserHelper.COLUMN_NIC, user.getNIC());
        values.put(UserHelper.COLUMN_EMAIL_ADDRESS, user.getEmailAddress());
        values.put(UserHelper.COLUMN_GENDER, user.getGender());
        values.put(UserHelper.COLUMN_MOBILE_PHONE_NUMBER, user.getMobilePhoneNumber());
        values.put(UserHelper.COLUMN_PROFILE_PICTURE_PATH, user.getProfilePicturePath());

        return database.update(UserHelper.TABLE_USER, values,
                UserHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(long id) {
        database.delete(UserHelper.TABLE_USER,
                UserHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_ID)));
        user.setName(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_PASSWORD)));
        user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_ADDRESS)));
        user.setLivingCity(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_LIVING_CITY)));
        user.setDateOfBirth(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_DATE_OF_BIRTH)));
        user.setNIC(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_NIC)));
        user.setEmailAddress(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_EMAIL_ADDRESS)));
        user.setGender(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_GENDER)));
        user.setMobilePhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_MOBILE_PHONE_NUMBER)));
        user.setProfilePicturePath(cursor.getString(cursor.getColumnIndexOrThrow(UserHelper.COLUMN_PROFILE_PICTURE_PATH)));
        return user;
    }
}
