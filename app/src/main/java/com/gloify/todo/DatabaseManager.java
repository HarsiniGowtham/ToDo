package com.gloify.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx){
        context = ctx;
    }

    public DatabaseManager open() throws SQLException{

        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void insert(String Message, String Date, String Time){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USER_MSG, Message);
        contentValues.put(DatabaseHelper.DATE, Date);
        contentValues.put(DatabaseHelper.TIME, Time);
        database.insert(DatabaseHelper.DATABASE_TABLE,null,contentValues);
    }

    public Cursor fetch(){
        String [] columns = new String[] {DatabaseHelper.USER_ID, DatabaseHelper.USER_MSG,
                DatabaseHelper.DATE, DatabaseHelper.TIME};
        Cursor cursor = database.query(DatabaseHelper.DATABASE_TABLE, columns,
                null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, String Message, String Date, String Time){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USER_MSG, Message);
        contentValues.put(DatabaseHelper.DATE, Date);
        contentValues.put(DatabaseHelper.TIME, Time);
        int ret = database.update(DatabaseHelper.DATABASE_TABLE, contentValues,
                DatabaseHelper.USER_ID + "=" + id, null);
        return ret;
    }

    public void delete(long id){
        database.delete(DatabaseHelper.DATABASE_TABLE,
                DatabaseHelper.USER_ID+ "=" + id,null);
    }

}
