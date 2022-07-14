package com.gloify.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "TODOLIST.DB";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE = "TODO_LIST";
    static final String USER_ID = "_ID";
    static final String USER_MSG = "Message";
    static final String DATE = "Date";
    static final String TIME = "Time";

    private static final String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ( " + USER_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_MSG + ", " + DATE + ", " + TIME + " );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

    }
}
