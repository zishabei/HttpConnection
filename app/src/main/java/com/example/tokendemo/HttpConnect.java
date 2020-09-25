package com.example.tokendemo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpConnect {

    private static final String TAG = "HttpConnect";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String DEFAULT_ENCODE = "UTF-8";
    private static final String URL = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";

    public static String operateSeparateToken(String method, String urlStr, String accessToken, String separateToken) {
        HttpURLConnection urlConn = null;

        try {
            URL url = new URL(urlStr);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod(method);
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            if (accessToken != null) {
                urlConn.setRequestProperty("Access-Token", accessToken);
            }
            if (separateToken != null) {
                urlConn.setRequestProperty("API-Token", separateToken);
            }
            urlConn.connect();
            int responseCode = urlConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConn.getInputStream();
                JSONObject result = readStreamedJSON(inputStream);
                Log.i(TAG, "operate separate token response:\n" + result);
                return result.toString(4);
            } else {
                InputStream errorStream = urlConn.getErrorStream();
                JSONObject result = readStreamedJSON(errorStream);
                Log.i(TAG, "operate separate token response:\n" + result);
                return result.toString(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    public static String getParameters(String urlStr, String separateToken) {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(urlStr);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod(METHOD_GET);
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            if (separateToken != null) {
                urlConn.setRequestProperty("API-Token", separateToken);
            }
            // TODO: 2020/09/18
            urlConn.setRequestProperty(ACCEPT_CHARSET, "UTF-8");
            urlConn.setRequestProperty(CONTENT_TYPE, "application/json;charset=utf-8");
            urlConn.connect();

            int responseCode = urlConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConn.getInputStream();
                JSONObject result = readStreamedJSON(inputStream);
                Log.i(TAG, "get parameters response:\n" + result);
                return result.toString(4);
            } else {
                InputStream errorStream = urlConn.getErrorStream();
                JSONObject result = readStreamedJSON(errorStream);
                Log.i(TAG, "get parameters response:\n" + result);
                return result.toString(4);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    public static String uploadData(String urlStr, String separateToken, String jsonStr) {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(urlStr);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod(METHOD_POST);
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            if (separateToken != null) {
                urlConn.setRequestProperty("API-Token", separateToken);
            }
            urlConn.setRequestProperty(ACCEPT_CHARSET, "UTF-8");
            urlConn.setRequestProperty(CONTENT_TYPE, "application/json;charset=utf-8");
            urlConn.connect();
            if (jsonStr != null) {
                DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
                String json = URLEncoder.encode(jsonStr, DEFAULT_ENCODE);
                out.writeBytes(json);
                out.flush();
                out.close();
            }

            int responseCode = urlConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConn.getInputStream();
                JSONObject result = readStreamedJSON(inputStream);
                Log.i(TAG, "get parameters response:\n" + result);
                return result.toString(4);
            } else {
                InputStream errorStream = urlConn.getErrorStream();
                JSONObject result = readStreamedJSON(errorStream);
                Log.i(TAG, "get parameters response:\n" + result);
                return result.toString(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
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
