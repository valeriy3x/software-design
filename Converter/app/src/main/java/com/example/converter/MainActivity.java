package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private DataFragment mDataFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataFragment = (DataFragment) this.getSupportFragmentManager().findFragmentById(R.id.dataFragment);
    }

    public void swapValues(View view) {
        mDataFragment.swapValues();
    }

    public void copyEntry(View view) {
        mDataFragment.copyEntry();
    }

    public void copyOutput(View view) {
        mDataFragment.copyResult();
    }
}