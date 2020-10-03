package com.example.converter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

import java.util.List;
import java.util.Objects;

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

    public static DataFragment newInstance() {
        return new DataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.data_fragment, container, false);

        initComponents(fragmentView);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(DataViewModel.class);
        mViewModel.init();
        initSpinners(Objects.requireNonNull(getView()));
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
                List<String> dataSet = mViewModel.getCurrentCategories();

                adapterInput.clear();
                adapterInput.addAll(dataSet);
                adapterInput.notifyDataSetChanged();

                adapterOutput.clear();
                adapterOutput.addAll(dataSet);
                adapterOutput.notifyDataSetChanged();
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

    private void initComponents(View view) {
        mInputEditText = view.findViewById(R.id.edittext_input);
        mOutputEditText = view.findViewById(R.id.edittext_output);
        mUnitSpinner = view.findViewById(R.id.spinner_unit);
        mInputSpinner = view.findViewById(R.id.spinner_entry);
        mOutputSpinner = view.findViewById(R.id.spinner_result);
    }

    private void initSpinners(View view) {
        adapterUnit = ArrayAdapter.createFromResource(view.getContext(), R.array.units, R.layout.spinner_unit);
        adapterUnit.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mUnitSpinner.setAdapter(adapterUnit);

        adapterInput = new ArrayAdapter<String>(view.getContext(),
                R.layout.support_simple_spinner_dropdown_item, mViewModel.getCurrentCategories());
        mInputSpinner.setAdapter(adapterInput);

        adapterOutput = new ArrayAdapter<String>(view.getContext(),
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
    }

    public void swapValues() {
        mViewModel.swapValues();
    }

    public void copyEntry() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.copied_entry), mInputEditText.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(getActivity(), R.string.toast_copy, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void copyResult() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.copied_output), mOutputEditText.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(getActivity(), R.string.toast_copy, Toast.LENGTH_SHORT);
        toast.show();
    }


}