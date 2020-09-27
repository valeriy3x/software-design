package com.example.converter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.converter.data.Unit;
import com.example.converter.viewmodels.DataViewModel;

public class DataFragment extends Fragment {

    private DataViewModel mViewModel;
    private EditText mInputEditText;
    private EditText mOutputEditText;
    private Spinner mUnitSpinner;
    private Spinner mInputSpinner;
    private Spinner mOutputSpinner;
    private ArrayAdapter<CharSequence> adapterUnit;
    private ArrayAdapter<String> adapterInput;
    private ArrayAdapter<String> adapterOutput;
    private ImageButton mSwapButton;
    private Button mCopyInputButton;
    private Button mCopyOutputButton;

    public static DataFragment newInstance() {
        return new DataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        mViewModel.init();
        initComponents();
        mViewModel.getInputValue().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mInputEditText.setText(s);
                mOutputEditText.setText(mViewModel.calculateOutput());
            }
        });

        mViewModel.getCurrentUnit().observe(getViewLifecycleOwner(), new Observer<Unit>() {
            @Override
            public void onChanged(Unit unit) {
                adapterInput = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mViewModel.getCurrentCategories());
                mInputSpinner.setAdapter(adapterInput);

                adapterOutput = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mViewModel.getCurrentCategories());
                mOutputSpinner.setAdapter(adapterInput);
            }
        });



        mViewModel.getInputMeasurement().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                int spinnerPosition = adapterInput.getPosition(s);
                mInputSpinner.setSelection(spinnerPosition);
            }
        });

        mViewModel.getOutputMeasurement().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                int spinnerPosition = adapterOutput.getPosition(s);
                mOutputSpinner.setSelection(spinnerPosition);
            }
        });
    }

    private void initComponents() {
        mInputEditText = getActivity().findViewById(R.id.edittext_input);
        mOutputEditText = getActivity().findViewById(R.id.edittext_output);
        mUnitSpinner = getActivity().findViewById(R.id.spinner_unit);
        mInputSpinner = getActivity().findViewById(R.id.spinner_entry);
        mOutputSpinner = getActivity().findViewById(R.id.spinner_result);
        mSwapButton = getActivity().findViewById(R.id.button_swap);
        mCopyInputButton = getActivity().findViewById(R.id.button_copy_entry);
        mCopyOutputButton = getActivity().findViewById(R.id.button_copy_result);

        adapterUnit = ArrayAdapter.createFromResource(getActivity(), R.array.units, R.layout.spinner_unit);
        adapterUnit.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mUnitSpinner.setAdapter(adapterUnit);

        adapterInput = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, mViewModel.getCurrentCategories());
        mInputSpinner.setAdapter(adapterInput);
        adapterOutput = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, mViewModel.getCurrentCategories());
        mOutputSpinner.setAdapter(adapterOutput);

        mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setUnitConverter(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mInputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setInputMeasurement(parent.getItemAtPosition(position).toString());
                mOutputEditText.setText(mViewModel.calculateOutput());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mOutputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.setOutputMeasurement(parent.getItemAtPosition(position).toString());
                mOutputEditText.setText(mViewModel.calculateOutput());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSwapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.swapValues();
            }
        });

        mCopyInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.copied_entry), mInputEditText.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(getActivity(), R.string.toast_copy, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        mCopyOutputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(getString(R.string.copied_output), mOutputEditText.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast toast = Toast.makeText(getActivity(), R.string.toast_copy, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }


}