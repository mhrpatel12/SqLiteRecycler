package com.sqlite.recycler.database.model;

/**
 * Created by mihir on 21/04/18.
 */

public class User {
    public static final String TABLE_USERS = "users";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_DESCRIPTION = "user_description";

    private int id;
    private String userName;
    private String userDescription;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_USERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USER_NAME + " TEXT,"
                    + COLUMN_USER_DESCRIPTION + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public User() {
    }

    public User(int id, String userName, String userDescription) {
        this.id = id;
        this.userName = userName;
        this.userDescription = userDescription;
    }

    public User(String userName, String userDescription) {
        this.userName = userName;
        this.userDescription = userDescription;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }
}
