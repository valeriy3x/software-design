package com.example.converter.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.converter.data.AngleConverter;
import com.example.converter.data.SpeedConverter;
import com.example.converter.data.Unit;
import com.example.converter.data.UnitConverter;
import com.example.converter.data.WeightConverter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DataViewModel extends ViewModel {
    private UnitConverter unitConverter;
    private MutableLiveData<Unit> currentUnit = new MutableLiveData<>();
    private MutableLiveData<String> inputValue = new MutableLiveData<String>("0");;
    private MutableLiveData<String> inputMeasurement = new MutableLiveData<>();
    private MutableLiveData<String> outputMeasurement = new MutableLiveData<>();


    public void init() {
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
        String input = inputValue.getValue();
        if (input.endsWith("."))
            input = input.substring(0, input.length() - 1);

        Double result = unitConverter.calculateResult(inputMeasurement.getValue(),
                outputMeasurement.getValue(),
                Double.parseDouble(inputValue.getValue()));

        return String.format(Locale.US, "%.4f", result);
    }

    public void swapValues() {
        String outputValue = calculateOutput();
        String entryMeasurement = inputMeasurement.getValue();
        inputMeasurement.setValue(outputMeasurement.getValue());
        outputMeasurement.setValue(entryMeasurement);
        inputValue.setValue(outputValue);
    }

    public void insertChar(String newChar) {
        String newValue = inputValue.getValue();
        assert newValue != null;
        if (newValue.equals("0") && !newChar.equals("."))
            newValue = newChar;
        else
            newValue += newChar;
        inputValue.setValue(newValue);
    }

    public void removeChar() {
        if (Objects.equals(inputValue.getValue(), "0"))
            return;
        String newValue = inputValue.getValue();
        newValue = removeLastChar(newValue);
        if (newValue.isEmpty())
            newValue = "0";
        inputValue.setValue(newValue);
    }

    private static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    private static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }


}