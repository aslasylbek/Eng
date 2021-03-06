package com.uibenglish.aslan.mvpmindorkssample.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Grammar;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Reading;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Topic;
import com.uibenglish.aslan.mvpmindorkssample.ui.main.content.Word;

import static android.support.constraint.Constraints.TAG;

@Database(entities = {Topic.class, Word.class, Reading.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract TopicDao topicDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .allowMainThreadQueries()
                            .addMigrations(AppDatabase.MIGRATION_1_2)
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .build();
        }
        return INSTANCE;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("drop table if exists grammar");
        }
    };


    public static void destroyInstance() {
        INSTANCE = null;
    }


}
