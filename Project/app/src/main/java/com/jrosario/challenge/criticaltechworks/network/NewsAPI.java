package com.jrosario.challenge.criticaltechworks.network;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsAPI {
    @GET("v2/top-headlines")
    Call<JsonObject> getTopHeadlines(@QueryMap Map<String, String> parameters);
}
