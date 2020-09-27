package com.example.converter.data;

import java.util.List;

public interface UnitConverter {
    Double getMultiplier(String name);
    List<String> getCategories();
}
