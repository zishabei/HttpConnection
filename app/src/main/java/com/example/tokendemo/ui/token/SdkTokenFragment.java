package com.example.tokendemo.ui.token;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tokendemo.R;
import com.example.tokendemo.costom.EditTextWatcher;
import com.example.tokendemo.databinding.FragmentSdkTokenBinding;

public class SdkTokenFragment extends Fragment {

    private static final String TAG = "SdkTokenFragment";
    private SdkTokenViewModel mSdkTokenViewModel;
    private FragmentSdkTokenBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sdk_token, container, false);
        mSdkTokenViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(SdkTokenViewModel.class);
        mBinding.setSdkTokenData(mSdkTokenViewModel);
        mBinding.setLifecycleOwner(this);
        View root = mBinding.getRoot();
        parentLayoutRequestFocus();
        initEditText();
        initProgress();
        initCheckBox();
        return root;
    }

    private void initCheckBox() {
        mBinding.accessTokenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSdkTokenViewModel.setAccessTokenChecked(isChecked);
            }
        });
        mBinding.separateTokenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSdkTokenViewModel.setLeftSeparateTokenChecked(isChecked);
            }
        });
    }

    private void initProgress() {
        mSdkTokenViewModel.getShowLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mBinding.progressLL.setVisibility(View.VISIBLE);
                } else {
                    mBinding.progressLL.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initEditText() {
        EditTextWatcher urlWatcher = new EditTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mSdkTokenViewModel.setTokenUrl(String.valueOf(s));
            }
        };
        mBinding.apiEt.addTextChangedListener(urlWatcher);
        EditTextWatcher accessTokenWatcher = new EditTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mSdkTokenViewModel.setAccessToken(String.valueOf(s));
            }
        };
        mBinding.accessTokenEt.addTextChangedListener(accessTokenWatcher);
        EditTextWatcher separateTokenWatcher = new EditTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mSdkTokenViewModel.setSeparateToken(String.valueOf(s));
            }
        };
        mBinding.separateTokenEt.addTextChangedListener(separateTokenWatcher);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void parentLayoutRequestFocus() {
        mBinding.fragmentSdkTokenParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.fragmentSdkTokenParent.setFocusable(true);
                mBinding.fragmentSdkTokenParent.setFocusableInTouchMode(true);
                mBinding.fragmentSdkTokenParent.requestFocus();
                return false;
            }
        });
    }
}