package com.example.shivansh.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {


    public static  final String API = "dbc30b65-1fc6-11e9-9ee8-0200cd936042";
    public static final String BASE_URL1 = "https://a2841cac.ngrok.io/";
    private static Retrofit retrofit = null;

    public static Retrofit getSOSClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
