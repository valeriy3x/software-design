package com.example.converter.viewmodels;

import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.converter.repositories.DataRepository;

public class NumPadViewModel extends ViewModel {
    private DataRepository mRepo;

    public void init() {
        if (mRepo != null)
            return;
        mRepo = DataRepository.getInstance();
    }

    public void insert(String newInput) {
        mRepo.insertChar(newInput);
    }

    public void remove() {
        mRepo.removeChar();
    }
}