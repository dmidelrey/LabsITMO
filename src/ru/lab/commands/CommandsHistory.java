package ru.lab.commands;

import java.util.ArrayList;

public class CommandsHistory {
    private static ArrayList<String> history = new ArrayList<>();

    public static void addCommand(String commandName) {
        if (history.size() > 13) {
            history.remove(0);
        }
        history.add(commandName);
    }

    public static String getHistory() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : history) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }
}
