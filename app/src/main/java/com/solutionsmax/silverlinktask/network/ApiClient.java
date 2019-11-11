package com.solutionsmax.silverlinktask.network;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        try {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("ApiClient Exception", e.getMessage());
        }
        return retrofit;
    }
}
