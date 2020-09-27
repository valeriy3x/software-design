package com.example.converter.data;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AngleConverter implements UnitConverter {
    private Map<String, Double> mValuesTable = new HashMap<>();

    public AngleConverter() {
        mValuesTable.put("Degree", 1.000);
        mValuesTable.put("Radian", 0.017);
        mValuesTable.put("Grad", 1.111);
    }

    @Override
    public Double getMultiplier(String name) {
        if (mValuesTable.containsKey(name))
            return mValuesTable.get(name);
        else
            throw new IllegalArgumentException();
    }

    @Override
    public List<String> getCategories() {
        return new ArrayList<>(mValuesTable.keySet());
    }
}
