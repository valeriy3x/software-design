package com.example.converter.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightConverter implements UnitConverter {
    private Map<String, Double> mValuesTable = new HashMap<>();

    public WeightConverter() {
        mValuesTable.put("Kilogram", 1.000);
        mValuesTable.put("Pound", 2.205);
        mValuesTable.put("Ounce", 35.274);
    }

    @Override
    public Double getMultiplier(String name) {
        if (mValuesTable.containsKey(name))
            return mValuesTable.get(name);
        else
            throw new IllegalArgumentException();
    }

    public List<String> getCategories() {
        return new ArrayList<>(mValuesTable.keySet());
    }
}
