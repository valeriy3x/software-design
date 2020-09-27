package com.example.converter.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;

public class DataRepository {
    private MutableLiveData<String> inputNumber = new MutableLiveData<String>("0");

    private static DataRepository instance;

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getInputNumber() {
        return inputNumber;
    }

    public void insertChar(String newChar) {
        String newValue = inputNumber.getValue();
        if (newValue == "0" && newChar != ".")
            newValue = newChar;
        else
            newValue += newChar;
        inputNumber.postValue(newValue);
    }

    public void removeChar() {
        if (inputNumber.getValue() == "0")
            return;
        String newValue = inputNumber.getValue();
        newValue = removeLastChar(newValue);
        if (newValue.isEmpty())
            newValue = "0";
        inputNumber.postValue(newValue);
    }

    private static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    private static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }
}
