package com.jet.edu.project03.test;

public class FilterName implements Filter {

    public boolean filter(String name) {
        return name != null && !name.isEmpty() && name.startsWith(NAME);
    }
}
