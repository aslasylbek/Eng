package com.example.aslan.mvpmindorkssample.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.example.aslan.mvpmindorkssample.ui.main.content.Reading;
import com.example.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.example.aslan.mvpmindorkssample.ui.main.content.Word;

import static android.support.constraint.Constraints.TAG;

@Database(entities = {Topic.class, Word.class, Reading.class, Grammar.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract TopicDao topicDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .allowMainThreadQueries()
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .build();
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }


}
