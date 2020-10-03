package com.example.converter.data;

import java.util.List;

public interface UnitConverter {
    Double getMultiplier(String name);
    List<String> getCategories();

    default Double calculateResult(String inputMeasurement, String outputMeasurement, Double val) {
        Double inputMultiplier = this.getMultiplier(inputMeasurement);
        Double outputMultiplier = this.getMultiplier(outputMeasurement);
        Double multiplier = outputMultiplier / inputMultiplier;

        return multiplier * val;
    }
}
