package com.example.documentscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "Users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE Users(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR(255), data TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS Users");
    }

    public boolean insertUserData(User user) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();

        userValues.put("name", user.getName());
        userValues.put("data", user.getData());

        long result = DB.insert("Users", null, userValues);

        if (result == -1) {
            return false;
        }

        return true;
    }

    public User getUser(Integer id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Users WHERE id =?", new String[]{id.toString()});
        User user = new User();

        if (cursor.getCount() == 0) {
            return user;
        }

        return this.mountUser(cursor).get(0);
    }

    public ArrayList<User> getUsers() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Users", null);
        User user = new User();
        ArrayList<User> users = new ArrayList<>();

        if (cursor.getCount() == 0) {
            users.add(user);
            return users;
        }

        return this.mountUser(cursor);
    }

    private ArrayList<User> mountUser(Cursor cursor) {

        ArrayList<User> users = new ArrayList<>();
        cursor.moveToFirst();

        int userId = cursor.getColumnIndex("id");
        int name = cursor.getColumnIndex("name");
        int data = cursor.getColumnIndex("data");

        while (!cursor.isAfterLast()) {
            User user = new User();

            user.setId(cursor.getInt(userId));
            user.setName(cursor.getString(name));
            user.setData(cursor.getString(data));

            users.add(user);
            
            cursor.moveToNext();
        }

        return users;
    }
}
