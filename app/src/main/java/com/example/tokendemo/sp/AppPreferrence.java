package com.example.tokendemo.sp;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class AppPreferrence {

    private static final String TAG = "AppPreferrence";
    private static final String PREF_TOKEN_URL = "pref_token_url";
    private static final String PREF_ACCESS_TOKEN = "pref_access_token";


    private static final String PREF_DOWNLOAD_URL = "pref_download_url";
    private static final String PREF_UPLOAD_URL = "pref_upload_url";
    private static final String PREF_SEPARATE_TOKEN = "pref_separate_token";
    private static final String PREF_JSON_STRING = "pref_json_string";
    private static final String PREF_INTERVAL = "pref_interval";

    private static final String PREF_ACCESS_TOKEN_CHECKED = "pref_access_token_checked";
    private static final String PREF_LEFT_SEPARATE_TOKEN_CHECKED = "pref_left_separate_token_checked";
    private static final String PREF_CENTER_SEPARATE_TOKEN_CHECKED = "pref_center_separate_token_checked";
    private static final String PREF_RIHGT_SEPARATE_TOKEN_CHECKED = "pref_right_separate_token_checked";
    private static final String PREF_JSON_CHECKED = "pref_json_checked";

    public static void setTokenUrl(Context context, String url) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putString(PREF_TOKEN_URL, url);
    }

    public static String getTokenUrl(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getString(PREF_TOKEN_URL, "https://st-api.datatakt.jp/v1/token");
    }

    public static void setAccessToken(Context context, String url) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putString(PREF_ACCESS_TOKEN, url);
    }

    public static String getAccessToken(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getString(PREF_ACCESS_TOKEN, "0000");
    }

    public static void setDownloadUrl(Context context, String url) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putString(PREF_DOWNLOAD_URL, url);
    }

    public static String getDownloadUrl(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getString(PREF_DOWNLOAD_URL, "https://st-api.datatakt.jp/v1/parameters");
    }

    public static void setUploadUrl(Context context, String url) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putString(PREF_UPLOAD_URL, url);
    }

    public static String getUploadUrl(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getString(PREF_UPLOAD_URL, "https://st-api.datatakt.jp/v1/device-data/raws");
    }

    public static void setJsonString(Context context, String json) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putString(PREF_JSON_STRING, json);
    }

    public static String getJsonString(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getString(PREF_JSON_STRING, getDefaluteJsonString());
    }

    private static String getDefaluteJsonString() {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.JAPAN);
            String str = format.format(new Date());
            //"usingCaFlag":0,"radioPhoneType":1,"localeLangCode":"en",
            JSONObject deviceDataObj = new JSONObject();
            deviceDataObj.put("usingCaFlag", 0);
            deviceDataObj.put("usingCaFlag", 1);
            deviceDataObj.put("localeLangCode", "en");
            JSONObject obj = new JSONObject();
            obj.put("deviceData", deviceDataObj);
            obj.put("deviceDate", str);
            return obj.toString(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void setSeparateToken(Context context, String token) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putString(PREF_SEPARATE_TOKEN, token);
    }

    public static String getSeparateToken(final Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getString(PREF_SEPARATE_TOKEN, "");
    }

    public static void setAccessTokenChecked(Context context, boolean checked) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putBoolean(PREF_ACCESS_TOKEN_CHECKED, checked);
    }

    public static boolean getAccessTokenChecked(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getBoolean(PREF_ACCESS_TOKEN_CHECKED, true);
    }

    public static void setLeftSeparateTokenChecked(Context context, boolean checked) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putBoolean(PREF_LEFT_SEPARATE_TOKEN_CHECKED, checked);
    }

    public static boolean getLeftSeparateTokenChecked(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getBoolean(PREF_LEFT_SEPARATE_TOKEN_CHECKED, false);
    }

    public static void setCenterSeparateTokenChecked(Context context, boolean checked) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putBoolean(PREF_CENTER_SEPARATE_TOKEN_CHECKED, checked);
    }

    public static boolean getCenterSeparateTokenChecked(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getBoolean(PREF_CENTER_SEPARATE_TOKEN_CHECKED, true);
    }

    public static void setRihgtSeparateTokenChecked(Context context, boolean checked) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putBoolean(PREF_RIHGT_SEPARATE_TOKEN_CHECKED, checked);
    }

    public static boolean getRihgtSeparateTokenChecked(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getBoolean(PREF_RIHGT_SEPARATE_TOKEN_CHECKED, true);
    }

    public static void setJsonChecked(Context context, boolean checked) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putBoolean(PREF_JSON_CHECKED, checked);
    }

    public static boolean getJsonChecked(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getBoolean(PREF_JSON_CHECKED, true);
    }

    public static int getInterval(Context context) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        return pref.getInt(PREF_INTERVAL, 0);
    }

    public static void setInterval(Context context, int interval) {
        AppPreferenceManager pref = new AppPreferenceManager(context);
        pref.putInt(PREF_INTERVAL, interval);
    }
}

