package com.jrosario.challenge.criticaltechworks.network;

import android.util.Log;

import com.google.gson.JsonObject;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private static NewsAPI service;
    private static APIManager apiManager;
    private final String TAG = APIManager.class.getName();

    private APIManager() {
        setService();
    }

    private void setService(){

        OkHttpClient client=new OkHttpClient();
        try {
            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();

            if (tlsSocketFactory.getTrustManager()!=null) {
                client = new OkHttpClient.Builder()
                        .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                        .readTimeout(30, TimeUnit.SECONDS)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .build();
            }
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            Log.e(TAG, "Error - ", exception);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(NewsAPI.class);
    }

    public static APIManager getInstance() {
        if (apiManager == null) {
            apiManager = new APIManager();
        }
        return apiManager;
    }

    public void getTopHeadlines(Map<String, String> parameters, Callback<JsonObject> callback) {
        Call<JsonObject> bodyCall = service.getTopHeadlines(parameters);
        bodyCall.enqueue(callback);
    }
}
