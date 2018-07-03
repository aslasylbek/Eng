package com.example.aslan.mvpmindorkssample.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {

    private static DBOpenHelper instance = null;
    private static SQLiteDatabase mSqliteDataBase = null;

    public static DBOpenHelper getInstance(Context context){
        if (instance==null)
            instance = new DBOpenHelper(context, 1);
        return instance;
    }

    public static SQLiteDatabase getmSqliteDataBase(){
        if (mSqliteDataBase==null)
            mSqliteDataBase = instance.getWritableDatabase();
        return mSqliteDataBase;
    }

    public long insertData(ContentValues contentValues, String tableName) {
        long id = getmSqliteDataBase().insert(tableName, null, contentValues);
        return id;
    }





}
