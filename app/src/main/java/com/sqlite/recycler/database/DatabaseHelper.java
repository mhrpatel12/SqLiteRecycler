package com.sqlite.recycler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sqlite.recycler.database.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mihir on 21/04/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "users_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(User.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    public long insertUser(User user) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(User.COLUMN_USER_NAME, user.getUserName());
        values.put(User.COLUMN_USER_DESCRIPTION, user.getUserDescription());

        // insert row
        long id = db.insert(User.TABLE_USERS, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public User getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.TABLE_USERS,
                new String[]{User.COLUMN_ID, User.COLUMN_USER_NAME, User.COLUMN_USER_DESCRIPTION},
                User.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        User note = new User(
                cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_NAME)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_DESCRIPTION)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<User> getAllNotes() {
        List<User> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + User.TABLE_USERS + " ORDER BY " +
                User.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User note = new User();
                note.setId(cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)));
                note.setUserName(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_NAME)));
                note.setUserDescription(cursor.getString(cursor.getColumnIndex(User.COLUMN_USER_DESCRIPTION)));

                notes.add(note);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + User.TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }
}
