package com.solutionsmax.silverlinktask.network;

import com.solutionsmax.silverlinktask.model.FactsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("facts.json")
    Call<FactsResponse> getFacts();
}
