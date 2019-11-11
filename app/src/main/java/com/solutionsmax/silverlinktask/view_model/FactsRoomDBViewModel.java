package com.solutionsmax.silverlinktask.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.solutionsmax.silverlinktask.db.Facts;
import com.solutionsmax.silverlinktask.db.FactsRepository;

import java.util.List;

public class FactsRoomDBViewModel extends AndroidViewModel {
    private FactsRepository factsRepository;
    private LiveData<List<Facts>> factsLiveData;

    public FactsRoomDBViewModel(@NonNull Application application) {
        super(application);
        factsRepository = new FactsRepository(application);
        factsLiveData = factsRepository.getFacts();
    }

    public LiveData<List<Facts>> getCachedFacts() {
        return factsLiveData;
    }

    public void insert(Facts facts) {
        factsRepository.insert(facts);
    }
}
