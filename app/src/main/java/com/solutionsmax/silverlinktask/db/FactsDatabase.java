package com.solutionsmax.silverlinktask.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Facts.class}, version = 1, exportSchema = false)
public abstract class FactsDatabase extends RoomDatabase {
    public abstract FactsDAO dao();

    private static FactsDatabase INSTANCE;

    static FactsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FactsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FactsDatabase.class, "facts_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(mCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback mCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateFactsAsync(INSTANCE).execute();
        }
    };

    private static class PopulateFactsAsync extends AsyncTask<Void, Void, Void> {

        private final FactsDAO factsDAO;

        public PopulateFactsAsync(FactsDatabase instance) {
            factsDAO = instance.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Start the app with a clean database every time.
            factsDAO.deleteAll();
            return null;
        }
    }
}
