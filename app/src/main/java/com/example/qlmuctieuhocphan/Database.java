package com.example.qlmuctieuhocphan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "QLDIEMHOCPHAN.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users(id INTEGER NOT NULL PRIMARY KEY, fullname TEXT NOT NULL, username TEXT NOT NULL, password TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS courses(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, time1 REAL DEFAULT 0.0, time2 REAL DEFAULT 0.0, target REAL DEFAULT 0.0)");
        db.execSQL("INSERT INTO users(id, fullname, username, password) VALUES (1, 'Admin', 'admin', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS courses");
        onCreate(db);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ? and password = ?", new String[]{ username, password });
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public Cursor getData(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table + " ORDER BY id DESC", null);
        return cursor;
    }

    public boolean updateUser(String id, String fullname, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname",fullname);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long user = db.update("users", contentValues, "id = ?", new String[]{id});
        if (user == -1) return false;
        else return true;
    }

    public boolean addCourse(String name, double time1, double time2, double target) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("time1", time1);
        contentValues.put("time2", time2);
        contentValues.put("target", target);
        long course = db.insert("courses", null, contentValues);
        if (course == -1) return false;
        else return true;
    }

    public boolean updateCourse(int id, String name, double time1, double time2, double target) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("time1", time1);
        contentValues.put("time2", time2);
        contentValues.put("target", target);
        long course = db.update("courses", contentValues, "id = ?", new String[]{String.valueOf(id)});
        if (course == -1) return false;
        else return true;
    }

    public boolean deleteCourse(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        long course = db.delete("courses", "id = ?", new String[]{id});
        if (course == -1) return false;
        else return true;
    }
}
