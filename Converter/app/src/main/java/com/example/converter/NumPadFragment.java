package com.example.converter;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.converter.viewmodels.DataViewModel;

public class NumPadFragment extends Fragment {

    private DataViewModel mViewModel;

    Button mDeleteButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.num_pad_fragment, container, false);
        setDigitNumbersListeners(fragmentView);
        mDeleteButton = fragmentView.findViewById(R.id.button_numDelete);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);


        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.removeChar();
            }
        });


    }

    private void setDigitNumbersListeners(View view) {
        setListener(view.findViewById(R.id.button_num1));
        setListener(view.findViewById(R.id.button_num5));
        setListener(view.findViewById(R.id.button_num2));
        setListener(view.findViewById(R.id.button_num3));
        setListener(view.findViewById(R.id.button_num6));
        setListener(view.findViewById(R.id.button_num7));
        setListener(view.findViewById(R.id.button_num8));
        setListener(view.findViewById(R.id.button_num9));
        setListener(view.findViewById(R.id.button_num4));
        setListener(view.findViewById(R.id.button_numDot));
        setListener(view.findViewById(R.id.button_num0));

    }

    private void setListener(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel != null)
                    mViewModel.insertChar(button.getText().toString());
            }
        });
    }

}