package com.example.pranav.bakingtime.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {


    private static final String BAKINGDB_BASE = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking";
    private static final String JSON_TOKEN = "baking.json" ;
    public static final String FULL_URL = BAKINGDB_BASE+"/"+JSON_TOKEN;
    private static OkHttpClient client = new OkHttpClient();


    public static String getResponseFromAPI(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

        }
}
