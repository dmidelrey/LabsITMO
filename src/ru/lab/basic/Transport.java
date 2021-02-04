package ru.lab.basic;

import java.util.ArrayList;

public enum Transport {
    NONE,
    LITTLE,
    NORMAL;

    public static ArrayList<String> getArrayList() {
        Transport[] transports = Transport.values();
        ArrayList<String> result = new ArrayList<>();
        for (Transport transport : transports) {
            result.add(transport.toString());
        }
        return result;
    }
}
