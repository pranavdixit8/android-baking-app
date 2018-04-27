package com.example.pranav.bakingtime.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {


    private static final String BAKINGDB_BASE = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking";
    private static final String JSON_TOKEN = "baking.json" ;



    public static URL buildRecipeStepVideoURL(String stepURL){
        Uri uri = Uri.parse(stepURL).buildUpon().build();
        URL url =null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static URL buildURL(){
        Uri uri = Uri.parse(BAKINGDB_BASE).buildUpon()
                .appendPath(JSON_TOKEN)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }



    public static String getResponseFromAPI(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(10000);

            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");

            boolean hasData = sc.hasNext();
            if (hasData) {
                return sc.next();
            } else {
                return null;
            }
        }
        finally{
            urlConnection.disconnect();
        }
    }
}
