package com.example.tokendemo.ui.token;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tokendemo.HttpConnect;
import com.example.tokendemo.sp.AppPreferrence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

public class SdkTokenViewModel extends AndroidViewModel {

    private MutableLiveData<String> mTokenUrl;
    private MutableLiveData<String> mAccessToken;
    private MutableLiveData<String> mSeparateToken;
    private MutableLiveData<String> mOperateTokenResponse;
    private MutableLiveData<Boolean> mShowLoading;
    private MutableLiveData<Boolean> mAccessTokenChecked;
    private MutableLiveData<Boolean> mLeftSeparateTokenChecked;

    public SdkTokenViewModel(@NonNull Application application) {
        super(application);
        getShowLoading();
    }

    public MutableLiveData<String> getTokenUrl() {
        if (mTokenUrl == null) {
            String tokenUrl = AppPreferrence.getTokenUrl(getApplication());
            mTokenUrl = new MutableLiveData<>(tokenUrl);
        }
        return mTokenUrl;
    }

    public void setTokenUrl(String url) {
        AppPreferrence.setTokenUrl(getApplication(), url);
        mTokenUrl.postValue(url);
    }

    public MutableLiveData<String> getAccessToken() {
        if (mAccessToken == null) {
            String accessToken = AppPreferrence.getAccessToken(getApplication());
            mAccessToken = new MutableLiveData<>(accessToken);
        }
        return mAccessToken;
    }

    public void setAccessToken(String token) {
        AppPreferrence.setAccessToken(getApplication(), token);
        mAccessToken.postValue(token);
    }

    public MutableLiveData<String> getSeparateToken() {
        if (mSeparateToken == null) {
            String accessToken = AppPreferrence.getSeparateToken(getApplication());
            mSeparateToken = new MutableLiveData<>(accessToken);
        }
        return mSeparateToken;
    }

    public void setSeparateToken(String token) {
        AppPreferrence.setSeparateToken(getApplication(), token);
        mSeparateToken.postValue(token);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        if (mShowLoading == null) {
            mShowLoading = new MutableLiveData<>(false);
        }
        return mShowLoading;
    }

    public MutableLiveData<String> getRequstTokenResponse() {
        if (mOperateTokenResponse == null) {
            mOperateTokenResponse = new MutableLiveData<>("");
        }
        return mOperateTokenResponse;
    }

    public MutableLiveData<Boolean> getAccessTokenChecked() {
        if (mAccessTokenChecked == null) {
            boolean accessTokenChecked = AppPreferrence.getAccessTokenChecked(getApplication());
            mAccessTokenChecked = new MutableLiveData<>(accessTokenChecked);
        }
        return mAccessTokenChecked;
    }

    public void setAccessTokenChecked(boolean checked) {
        AppPreferrence.setAccessTokenChecked(getApplication(), checked);
        mAccessTokenChecked.postValue(checked);
    }

    public MutableLiveData<Boolean> getLeftSeparateTokenChecked() {
        if (mLeftSeparateTokenChecked == null) {
            boolean leftSeparateTokenChecked = AppPreferrence.getLeftSeparateTokenChecked(getApplication());
            mLeftSeparateTokenChecked = new MutableLiveData<>(leftSeparateTokenChecked);
        }
        return mLeftSeparateTokenChecked;
    }

    public void setLeftSeparateTokenChecked(boolean checked) {
        AppPreferrence.setLeftSeparateTokenChecked(getApplication(), checked);
        mLeftSeparateTokenChecked.postValue(checked);
    }

    public void operateToken(final String method) {
        // TODO: 2020/09/23
        mShowLoading.postValue(true);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String response;
                Boolean accessTokenIsChecked = mAccessTokenChecked.getValue();
                Boolean separateTokenIsChecked = mLeftSeparateTokenChecked.getValue();
                if (accessTokenIsChecked == null || separateTokenIsChecked == null) {
                    return;
                }
                if (accessTokenIsChecked && separateTokenIsChecked) {
                    response = HttpConnect.operateSeparateToken(method, mTokenUrl.getValue(), mAccessToken.getValue(), mSeparateToken.getValue());
                } else if (accessTokenIsChecked) {
                    response = HttpConnect.operateSeparateToken(method, mTokenUrl.getValue(), mAccessToken.getValue(), null);
                } else if (separateTokenIsChecked) {
                    response = HttpConnect.operateSeparateToken(method, mTokenUrl.getValue(), null, mSeparateToken.getValue());
                } else {
                    response = HttpConnect.operateSeparateToken(method, mTokenUrl.getValue(), null, null);
                }

                try {
                    JSONObject object = new JSONObject(response);
                    mSeparateToken.postValue(object.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mOperateTokenResponse.postValue(response);

                mShowLoading.postValue(false);
            }
        });
    }
}