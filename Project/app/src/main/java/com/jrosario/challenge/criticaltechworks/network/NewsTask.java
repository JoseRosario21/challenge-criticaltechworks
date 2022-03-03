package com.jrosario.challenge.criticaltechworks.network;

import static com.jrosario.challenge.criticaltechworks.network.URLConstants.HTTP_RESPONSE_BAD_REQUEST;
import static com.jrosario.challenge.criticaltechworks.network.URLConstants.HTTP_RESPONSE_ERROR;
import static com.jrosario.challenge.criticaltechworks.network.URLConstants.HTTP_RESPONSE_NOT_FOUND;
import static com.jrosario.challenge.criticaltechworks.network.URLConstants.HTTP_RESPONSE_SERVER_ERROR;
import static com.jrosario.challenge.criticaltechworks.network.URLConstants.HTTP_RESPONSE_UNAUTHORIZED;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class NewsTask {
    private static final String TAG = NewsTask.class.getName();

    public static void getTopHeadlines(final HashMap<String, String> parameters, final Callback<JsonObject> callback) {
        APIManager.getInstance().getTopHeadlines(parameters, new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                switch(response.code()){
                    case URLConstants.HTTP_SUCCESSFUL:
                        if (response.body() != null) {
                            callback.returnResult(response.body());
                        }
                        break;
                    case URLConstants.HTTP_BAD_REQUEST:
                        callback.returnError(HTTP_RESPONSE_BAD_REQUEST);
                        break;
                    case URLConstants.HTTP_UNAUTHORIZED:
                        callback.returnError(HTTP_RESPONSE_UNAUTHORIZED);
                        break;
                    case URLConstants.HTTP_NOT_FOUND:
                        callback.returnError(HTTP_RESPONSE_NOT_FOUND);
                        break;
                    case URLConstants.HTTP_SERVER_ERROR:
                        callback.returnError(HTTP_RESPONSE_SERVER_ERROR);
                        break;
                    default:
                        callback.returnError(HTTP_RESPONSE_ERROR);
                        break;
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.i(TAG, "Throwable: " + t.getMessage());
                callback.returnError(t.getMessage());
            }
        });
    }
}
