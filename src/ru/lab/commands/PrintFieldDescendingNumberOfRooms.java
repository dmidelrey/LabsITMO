package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.controller.FlatController;

public class PrintFieldDescendingNumberOfRooms extends Command {
    @Override
    public void execute(FlatController flatController) {
        if (flatController.isEmpty()) {
            setMessage("Collection is empty\n");
        } else {
            setMessage("");
            for (Flat flat : flatController.getDescendingNumberOfRooms()){
                updateMessage(flat.toString() + "\n");
            }
        }

    }

    @Override
    public boolean commandValid() {
        CommandsHistory.addCommand("print_field_descending_numbers_of_rooms");
        return true;
    }
}
