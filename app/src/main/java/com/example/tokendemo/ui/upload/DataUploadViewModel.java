package com.example.tokendemo.ui.upload;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tokendemo.HttpConnect;
import com.example.tokendemo.sp.AppPreferrence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.Executors;

public class DataUploadViewModel extends AndroidViewModel {

    private static final String TAG = "DataUploadViewModel";
    private MutableLiveData<String> mUpLoadApi;
    private MutableLiveData<String> mDefaultSeparateToken;
    private MutableLiveData<String> mJsonStr;
    private MutableLiveData<Boolean> mShowLoading;
    private MutableLiveData<Boolean> mJsonChecked;
    private MutableLiveData<Boolean> mRightSeparateChecked;
    private MutableLiveData<String> mResponse;

    public DataUploadViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getUpLoadApi() {
        if (mUpLoadApi == null) {
            String uploadUrl = AppPreferrence.getUploadUrl(getApplication());
            mUpLoadApi = new MutableLiveData<>(uploadUrl);
        }
        return mUpLoadApi;
    }

    public void setUpLoadApi(String url) {
        AppPreferrence.setUploadUrl(getApplication(), url);
        mUpLoadApi.postValue(url);
    }

    public MutableLiveData<String> getSeparateToken() {
        if (mDefaultSeparateToken == null) {
            String token = AppPreferrence.getDefaultSeparateToken(getApplication());
            mDefaultSeparateToken = new MutableLiveData<>(token);
        }
        return mDefaultSeparateToken;
    }

    public void setSeparateToken(String token) {
        AppPreferrence.setDefaultSeparateToken(getApplication(), token);
        mDefaultSeparateToken.postValue(token);
    }

    public MutableLiveData<String> getJsonStr() {
        if (mJsonStr == null) {
            String jsonString = AppPreferrence.getJsonString(getApplication());
            mJsonStr = new MutableLiveData<>(jsonString);
        }
        return mJsonStr;
    }

    public void setJsonStr(String jsonString) {
        AppPreferrence.setJsonString(getApplication(), jsonString);
        mJsonStr.postValue(jsonString);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        if (mShowLoading == null) {
            mShowLoading = new MutableLiveData<>(false);
        }
        return mShowLoading;
    }

    public MutableLiveData<Boolean> getJsonChecked() {
        if (mJsonChecked == null) {
            boolean jsonChecked = AppPreferrence.getJsonChecked(getApplication());
            mJsonChecked = new MutableLiveData<>(jsonChecked);
        }
        return mJsonChecked;
    }

    public void setJsonChecked(boolean checked) {
        AppPreferrence.setJsonChecked(getApplication(), checked);
        mJsonChecked.postValue(checked);
    }

    public MutableLiveData<Boolean> getRightSeparateChecked() {
        if (mRightSeparateChecked == null) {
            boolean rightSeparateTokenChecked = AppPreferrence.getRihgtSeparateTokenChecked(getApplication());
            mRightSeparateChecked = new MutableLiveData<>(rightSeparateTokenChecked);
        }
        return mRightSeparateChecked;
    }

    public void setRightSeparateChecked(boolean checked) {
        AppPreferrence.setRihgtSeparateTokenChecked(getApplication(), checked);
        mRightSeparateChecked.postValue(checked);
    }

    public void resetJsonStr() {
        AppPreferrence.setJsonString(getApplication(), null);
        String jsonString = AppPreferrence.getJsonString(getApplication());
        AppPreferrence.setJsonString(getApplication(), jsonString);
        mJsonStr.postValue(jsonString);
    }

    public MutableLiveData<String> getResponse() {
        if (mResponse == null) {
            mResponse = new MutableLiveData<>("");
        }
        return mResponse;
    }


    public void upload() {
        // TODO: 2020/09/23
        mShowLoading.postValue(true);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Boolean separateCheckedValue = mRightSeparateChecked.getValue();
                Boolean jsonCheckedValue = mJsonChecked.getValue();
                if (separateCheckedValue == null || jsonCheckedValue == null) {
                    return;
                }
                String response;
                if (separateCheckedValue && jsonCheckedValue) {
                    response = HttpConnect.uploadData(mUpLoadApi.getValue(), mDefaultSeparateToken.getValue(), mJsonStr.getValue());
                } else if (separateCheckedValue) {
                    response = HttpConnect.uploadData(mUpLoadApi.getValue(), mDefaultSeparateToken.getValue(), null);
                } else if (jsonCheckedValue) {
                    response = HttpConnect.uploadData(mUpLoadApi.getValue(), null, mJsonStr.getValue());
                } else {
                    response = HttpConnect.uploadData(mUpLoadApi.getValue(), null, null);
                }
                mResponse.postValue(response);
                mShowLoading.postValue(false);
            }
        });
    }

}