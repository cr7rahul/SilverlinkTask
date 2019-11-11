package com.solutionsmax.silverlinktask.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.solutionsmax.silverlinktask.model.FactsListItem;
import com.solutionsmax.silverlinktask.model.FactsResponse;
import com.solutionsmax.silverlinktask.network.ApiClient;
import com.solutionsmax.silverlinktask.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FactsListViewModel extends ViewModel {
    private MutableLiveData<List<FactsListItem>> factsList;
    private MutableLiveData<String> sToolbarTitle;

    public LiveData<List<FactsListItem>> retrieveFactsList() {
        if (factsList == null) {
            factsList = new MutableLiveData<>();
            retrieveFacts();
        }
        return factsList;
    }


    public LiveData<String> toolbarTitle() {
        if (sToolbarTitle == null) {
            sToolbarTitle = new MutableLiveData<>();
            retrieveToolbarTitle();
        }
        return sToolbarTitle;
    }

    private void retrieveToolbarTitle() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getFacts().enqueue(new Callback<FactsResponse>() {
            @Override
            public void onResponse(@NonNull Call<FactsResponse> call, @NonNull Response<FactsResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    sToolbarTitle.setValue(response.body().getsTitle());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FactsResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void retrieveFacts() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getFacts().enqueue(new Callback<FactsResponse>() {
            @Override
            public void onResponse(@NonNull Call<FactsResponse> call, @NonNull Response<FactsResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    factsList.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(@NonNull Call<FactsResponse> call, @NonNull Throwable t) {

            }
        });
    }
}
