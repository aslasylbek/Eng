package com.example.aslan.mvpmindorkssample.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.NavigableMap;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "uib_english";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE = "USER_INFO";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BARCODE = "barcode";
    private static final String SAMPLE_QUERY = "CREATE TABLE IF NOT EXISTS "+ USER_TABLE + "("
            + ID+ " INTEGER primary key autoincrement, "
            + NAME + " VARCHAR(255), "
            + BARCODE + " VARCHAR(255));";


    public DBOpenHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SAMPLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
