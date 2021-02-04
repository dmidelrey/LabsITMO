package ru.lab.basic;

import java.util.ArrayList;

public enum View {
    STREET,
    YARD,
    PARK,
    BAD,
    GOOD;

    public static ArrayList<String> getArrayList() {
        View[] views = View.values();
        ArrayList<String> result = new ArrayList<>();
        for (View view : views) {
            result.add(view.toString());
        }
        return result;
    }
}