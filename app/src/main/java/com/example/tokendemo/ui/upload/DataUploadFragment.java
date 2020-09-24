package com.example.tokendemo.ui.upload;

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
import com.example.tokendemo.databinding.FragmentDataUploadBinding;

public class DataUploadFragment extends Fragment {

    private static final String TAG = "DataUploadFragment";
    private DataUploadViewModel mDataUploadViewModel;
    private FragmentDataUploadBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_upload, container, false);
        mDataUploadViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(DataUploadViewModel.class);
        mBinding.setUploadData(mDataUploadViewModel);
        mBinding.setLifecycleOwner(this);
        View root = mBinding.getRoot();
        initEditText();
        parentLayoutRequestFocus();
        initProgress();
        initCheckBox();
        return root;
    }

    private void initCheckBox() {
        mBinding.jsonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDataUploadViewModel.setJsonChecked(isChecked);
            }
        });
        mBinding.separaterTokenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDataUploadViewModel.setRightSeparateChecked(isChecked);
            }
        });
    }

    private void initProgress() {
        mDataUploadViewModel.getShowLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
                mDataUploadViewModel.setUpLoadApi(String.valueOf(s));
            }
        };
        mBinding.parametersApiEt.addTextChangedListener(urlWatcher);
        EditTextWatcher accessTokenWatcher = new EditTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mDataUploadViewModel.setSeparateToken(String.valueOf(s));
            }
        };
        mBinding.separateTokenEt.addTextChangedListener(accessTokenWatcher);
        EditTextWatcher jsonWatcher = new EditTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mDataUploadViewModel.setJsonStr(String.valueOf(s));
            }
        };
        mBinding.jsonEt.addTextChangedListener(jsonWatcher);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void parentLayoutRequestFocus() {
        mBinding.fragmentParametersParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.fragmentParametersParent.setFocusable(true);
                mBinding.fragmentParametersParent.setFocusableInTouchMode(true);
                mBinding.fragmentParametersParent.requestFocus();
                return false;
            }
        });
    }
}