package ru.lab.client;

import ru.lab.commands.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CommandData implements Serializable {
    private Command command;

    private Map<String, Command> commandMap = new HashMap<>();

    public CommandData(boolean isExecuteScript) {
        if (!isExecuteScript) {
            commandMap.put("execute_script", new ExecuteScript());
        }
        commandMap.put("login", new Login());
        commandMap.put("register", new Register());
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("show", new Show());
        commandMap.put("add", new Add());
        commandMap.put("update", new Update());
        commandMap.put("remove_by_id", new RemoveById());
        commandMap.put("clear", new Clear());
        commandMap.put("exit", new Exit());
        commandMap.put("add_if_min", new AddIfMin());
        commandMap.put("remove_lower", new RemoveLower());
        commandMap.put("history", new History());
        commandMap.put("remove_any_by_house", new RemoveAnyByHouse());
        commandMap.put("filter_less_than_transport", new FilterLessThanTransport());
        commandMap.put("print_field_descending_number_of_rooms", new PrintFieldDescendingNumberOfRooms());
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(String input) {
        if (input.isEmpty()) {
            return;
        }
        this.command = null;
        String[] values = input.split(" ");
        if (values.length == 1) {
            Command command = commandMap.get(values[0]);
            if (command != null) {
                command.setValue(null);
                this.command = command;
            } else {
                System.out.println("Command doesnt exist");
            }
        }
        if (values.length == 2) {
            Command command = commandMap.get(values[0]);
            if (command != null) {
                command.setValue(values[1]);
                this.command = command;
            } else {
                System.out.println("Command doesnt exist");
            }
        }
    }
    public boolean isValid() {
        return command.commandValid();
    }
}
