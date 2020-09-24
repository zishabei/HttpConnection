package com.example.tokendemo.ui.token;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tokendemo.HttpConnect;
import com.example.tokendemo.sp.AppPreferrence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

public class SdkTokenViewModel extends AndroidViewModel {

    private MutableLiveData<String> mTokenUrl;
    private MutableLiveData<String> mAccessToken;
    private MutableLiveData<String> mSeparateToken;
    private MutableLiveData<String> mRequstTokenResponse;
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
        if (mRequstTokenResponse == null) {
            mRequstTokenResponse = new MutableLiveData<>("");
        }
        return mRequstTokenResponse;
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

    public void requestToken() {
        // TODO: 2020/09/23
//        Toast.makeText(getApplication(), "取得", Toast.LENGTH_SHORT).show();
        mShowLoading.postValue(true);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String response = HttpConnect.requestSeparateToken(mTokenUrl.getValue(), mAccessToken.getValue());
                try {
                    JSONObject object = new JSONObject(response);
                    mSeparateToken.postValue(object.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mRequstTokenResponse.postValue(response);

                mShowLoading.postValue(false);
            }
        });
    }

    public void deleteToken() {
        // TODO: 2020/09/23
//        Toast.makeText(getApplication(), "削除", Toast.LENGTH_SHORT).show();
        mShowLoading.postValue(true);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mShowLoading.postValue(false);
            }
        });
    }

    public void refreshToken() {
        // TODO: 2020/09/23
//        Toast.makeText(getApplication(), "更新", Toast.LENGTH_SHORT).show();
        mShowLoading.postValue(true);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mShowLoading.postValue(false);
            }
        });
    }
}