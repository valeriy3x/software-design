package com.example.converter.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.converter.data.AngleConverter;
import com.example.converter.data.SpeedConverter;
import com.example.converter.data.Unit;
import com.example.converter.data.UnitConverter;
import com.example.converter.data.WeightConverter;
import com.example.converter.repositories.DataRepository;

import java.util.List;
import java.util.Locale;

public class DataViewModel extends ViewModel {
    private UnitConverter unitConverter;
    private MutableLiveData<Unit> currentUnit = new MutableLiveData<>();
    private MutableLiveData<String> inputValue;
    private MutableLiveData<String> inputMeasurement = new MutableLiveData<>();
    private MutableLiveData<String> outputMeasurement = new MutableLiveData<>();

    private DataRepository mRepo;

    public void init() {
        if (inputValue != null)
            return;
        mRepo = DataRepository.getInstance();
        inputValue = mRepo.getInputNumber();
        unitConverter = new AngleConverter();
        inputMeasurement.setValue("Degree");
        outputMeasurement.setValue("Radian");
        currentUnit.setValue(Unit.ANGLE);
    }

    public void setUnitConverter(String converterName) {
        switch (converterName) {
            case "Angle":
                unitConverter = new AngleConverter();
                currentUnit.setValue(Unit.ANGLE);
                inputMeasurement.setValue("Degree");
                outputMeasurement.setValue("Radian");
                break;
            case "Speed":
                unitConverter = new SpeedConverter();
                currentUnit.setValue(Unit.SPEED);
                inputMeasurement.setValue("Meter/Second");
                outputMeasurement.setValue("Kilometer/Hour");
                break;
            case "Weight":
                unitConverter = new WeightConverter();
                currentUnit.setValue(Unit.WEIGHT);
                inputMeasurement.setValue("Kilogram");
                outputMeasurement.setValue("Pound");
                break;
        }
    }


    public LiveData<String> getInputValue() {
        return inputValue;
    }

    public LiveData<String> getInputMeasurement() {
        return inputMeasurement;
    }

    public LiveData<String> getOutputMeasurement() {
        return outputMeasurement;
    }

    public LiveData<Unit> getCurrentUnit() { return currentUnit; }

    public List<String> getCurrentCategories() {
        return unitConverter.getCategories();
    }

    public void setInputMeasurement(String name) {
        inputMeasurement.setValue(name);
    }

    public void setOutputMeasurement(String name) {
        outputMeasurement.setValue(name);
    }

    public String calculateOutput() {
        Double inputMultiplier = unitConverter.getMultiplier(inputMeasurement.getValue());
        Double outputMultiplier = unitConverter.getMultiplier(outputMeasurement.getValue());
        Double multiplier = outputMultiplier / inputMultiplier;

        String input = inputValue.getValue();
        if (input.endsWith("."))
            input = input.substring(0, input.length() - 1);

        Double result = Double.parseDouble(input) * multiplier;
        return String.format(Locale.US, "%.4f", result);
    }

    public void swapValues() {
        String outputValue = calculateOutput();
        String entryMeasurement = inputMeasurement.getValue();
        inputMeasurement.setValue(outputMeasurement.getValue());
        outputMeasurement.setValue(entryMeasurement);
        inputValue.setValue(outputValue);
    }
}