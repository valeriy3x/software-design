package com.example.converter;

import androidx.lifecycle.ViewModelProviders;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.converter.viewmodels.NumPadViewModel;

public class NumPadFragment extends Fragment {

    private NumPadViewModel mViewModel;

    public static NumPadFragment newInstance() {
        return new NumPadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.num_pad_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NumPadViewModel.class);
        mViewModel.init();
        Button mDeleteButton = (Button) getActivity().findViewById(R.id.button_numDelete);

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.remove();
            }
        });

        setDigitNumbersListeners();

    }

    private void setDigitNumbersListeners() {
        setListener((Button) getActivity().findViewById(R.id.button_num1));
        setListener((Button) getActivity().findViewById(R.id.button_num2));
        setListener((Button) getActivity().findViewById(R.id.button_num3));
        setListener((Button) getActivity().findViewById(R.id.button_num4));
        setListener((Button) getActivity().findViewById(R.id.button_num5));
        setListener((Button) getActivity().findViewById(R.id.button_num6));
        setListener((Button) getActivity().findViewById(R.id.button_num7));
        setListener((Button) getActivity().findViewById(R.id.button_num8));
        setListener((Button) getActivity().findViewById(R.id.button_num9));
        setListener((Button) getActivity().findViewById(R.id.button_numDot));
        setListener((Button) getActivity().findViewById(R.id.button_num0));

    }

    private void setListener(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.insert(button.getText().toString());
            }
        });
    }

}