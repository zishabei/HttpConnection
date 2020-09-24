package com.example.tokendemo.ui.download;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tokendemo.HttpConnect;
import com.example.tokendemo.sp.AppPreferrence;

import java.util.concurrent.Executors;

public class DefinitionParametersViewModel extends AndroidViewModel {


    private MutableLiveData<String> mDownloadApi;
    private MutableLiveData<String> mSeparateToken;
    private MutableLiveData<Boolean> mShowLoading;
    private MutableLiveData<Boolean> mCenterSeparateTokenChecked;
    private MutableLiveData<String> mResponse;

    public DefinitionParametersViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<String> getParametersApi() {
        if (mDownloadApi == null) {
            String downloadUrl = AppPreferrence.getDownloadUrl(getApplication());
            mDownloadApi = new MutableLiveData<>(downloadUrl);
        }
        return mDownloadApi;
    }

    public void setParametersApi(String api) {
        AppPreferrence.setDownloadUrl(getApplication(), api);
        mDownloadApi.postValue(api);
    }

    public MutableLiveData<String> getSeparateToken() {
        if (mSeparateToken == null) {
            String token = AppPreferrence.getSeparateToken(getApplication());
            mSeparateToken = new MutableLiveData<>(token);
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

    public MutableLiveData<Boolean> getCenterSeparateTokenChecked() {
        if (mCenterSeparateTokenChecked == null) {
            boolean centerSeparateToken = AppPreferrence.getCenterSeparateTokenChecked(getApplication());
            mCenterSeparateTokenChecked = new MutableLiveData<>(centerSeparateToken);
        }
        return mCenterSeparateTokenChecked;
    }

    public void setCenterSeparateTokenChecked(boolean checked) {
        AppPreferrence.setCenterSeparateTokenChecked(getApplication(), checked);
        mCenterSeparateTokenChecked.postValue(checked);
    }

    public MutableLiveData<String> getResponse() {
        if (mResponse == null) {
            mResponse = new MutableLiveData<>("");
        }
        return mResponse;
    }

    public void getParameters() {
        // TODO: 2020/09/23
        mShowLoading.postValue(true);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String response;
                Boolean separateTokenChecked = mCenterSeparateTokenChecked.getValue();
                if (separateTokenChecked == null) {
                    return;
                }
                if (separateTokenChecked) {
                    response = HttpConnect.getParameters(mDownloadApi.getValue(), mSeparateToken.getValue());
                } else {
                    response = HttpConnect.getParameters(mDownloadApi.getValue(), null);
                }
                mResponse.postValue(response);
                // TODO: 2020/09/24 response处理

                mShowLoading.postValue(false);
            }
        });
    }
}