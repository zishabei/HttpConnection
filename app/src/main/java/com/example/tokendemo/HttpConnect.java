package com.example.tokendemo;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnect {

    private static final String TAG = "HttpConnect";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String CONTENT_TYPE = "Content-Type";
    private static final String URL = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";

    public static String requestSeparateToken(String urlStr, String appToken) {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(URL);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            urlConn.setRequestProperty("Access-Token", appToken);
            // TODO: 2020/09/18
            urlConn.setRequestProperty(ACCEPT_CHARSET, "UTF-8");
            urlConn.setRequestProperty(CONTENT_TYPE, "application/json;charset=utf-8");
            urlConn.connect();

            JSONObject result = readStreamedJSON(urlConn.getInputStream());
            Log.i(TAG, "response:" + result);
            return result.toString(4);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    public static void uploadData(){

    }


    private static JSONObject readStreamedJSON(InputStream in) throws JSONException, IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return new JSONObject(response.toString());
    }

}
