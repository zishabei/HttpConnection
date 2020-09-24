package com.example.tokendemo.ui.download;

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
import com.example.tokendemo.databinding.FragmentDefinitionParametersBinding;

public class DefinitionParametersFragment extends Fragment {

    private static final String TAG = "DefinitionParametersFra";
    private DefinitionParametersViewModel mDefinitionParametersViewModel;
    private FragmentDefinitionParametersBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_definition_parameters, container, false);
        mDefinitionParametersViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(DefinitionParametersViewModel.class);
        mBinding.setParametersData(mDefinitionParametersViewModel);
        mBinding.setLifecycleOwner(this);
        View root = mBinding.getRoot();
        parentLayoutRequestFocus();
        initEditText();
        initProgress();
        initCheckBox();
        return root;
    }

    private void initCheckBox() {
        mBinding.separaterTokenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDefinitionParametersViewModel.setCenterSeparateTokenChecked(isChecked);
            }
        });
    }

    private void initProgress() {
        mDefinitionParametersViewModel.getShowLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
                mDefinitionParametersViewModel.setParametersApi(String.valueOf(s));
            }
        };
        mBinding.parametersApiEt.addTextChangedListener(urlWatcher);
        EditTextWatcher accessTokenWatcher = new EditTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mDefinitionParametersViewModel.setSeparateToken(String.valueOf(s));
            }
        };
        mBinding.separateTokenEt.addTextChangedListener(accessTokenWatcher);
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