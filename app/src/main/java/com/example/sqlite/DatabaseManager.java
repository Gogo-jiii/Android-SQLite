package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDB.db";
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static int VERSION = 1;

    public DatabaseManager(@Nullable Context context, @Nullable String name,
                           @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        String query = "create table users (id integer primary key, name text)";
        db.execSQL(query);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS users";
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insert(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        database.insert(USERS_TABLE_NAME, null, values);
        database.close();
        return true;
    }

    public UsersModel getUser(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(USERS_TABLE_NAME, new String[]{USERS_COLUMN_ID,
                        USERS_COLUMN_NAME},
                USERS_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        UsersModel model = new UsersModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));

        return model;
    }

    public List<UsersModel> getAllUsers() {
        List<UsersModel> list = new ArrayList();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from users", null);

        if (cursor.moveToFirst()) {
            do {
                UsersModel model = new UsersModel();
                model.setID(Integer.parseInt(cursor.getString(0)));
                model.setName(cursor.getString(1));

                list.add(model);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getNumberOfRows() {
        SQLiteDatabase database = this.getReadableDatabase();
        int numOfRows = (int) DatabaseUtils.queryNumEntries(database, USERS_TABLE_NAME);
        return numOfRows;
    }

    public boolean update(int id, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);

        database.update(USERS_TABLE_NAME, values, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public int delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(USERS_TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
    }
}
