package com.solutionsmax.silverlinktask.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FactsRepository {
    private FactsDAO factsDAO;
    private LiveData<List<Facts>> factsLiveData;

    public FactsRepository(Application application) {
        FactsDatabase factsDatabase = FactsDatabase.getDatabase(application);
        factsDAO = factsDatabase.dao();
        factsLiveData = factsDAO.getAllfacts();
    }

    public LiveData<List<Facts>> getFacts() {
        return factsLiveData;
    }

    public void insert(Facts facts) {
        new InsertAsyncTask(factsDAO).execute(facts);
    }

    private static class InsertAsyncTask extends AsyncTask<Facts, Void, Void> {
        FactsDAO asyncFactsDAO;

        InsertAsyncTask(FactsDAO factsDAO) {
            asyncFactsDAO = factsDAO;
        }

        @Override
        protected Void doInBackground(Facts... facts) {
            asyncFactsDAO.insert(facts[0]);
            return null;
        }
    }
}
