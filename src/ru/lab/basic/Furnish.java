package ru.lab.basic;

import java.util.ArrayList;

public enum Furnish {
    DESIGNER,
    NONE,
    BAD,
    LITTLE;

    public static ArrayList<String> getArrayList() {
        Furnish[] furnishes = Furnish.values();
        ArrayList<String> result = new ArrayList<>();
        for (Furnish furnish : furnishes) {
            result.add(furnish.toString());
        }
        return result;
    }
}