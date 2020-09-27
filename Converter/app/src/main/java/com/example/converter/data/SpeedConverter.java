package com.example.converter.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeedConverter implements UnitConverter {
    private Map<String, Double> mValuesTable = new HashMap<>();

    public SpeedConverter() {
        mValuesTable.put("Meter/Second", 1.000);
        mValuesTable.put("Kilometer/Hour", 3.600);
        mValuesTable.put("Mile/Hour", 2.237);
    }

    @Override
    public Double getMultiplier(String name) {
        //TODO: Implement null pointer exception check
        return mValuesTable.get(name);
    }

    public List<String> getCategories() {
        return new ArrayList<>(mValuesTable.keySet());
    }
}
