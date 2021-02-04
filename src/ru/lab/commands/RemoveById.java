package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.controller.FlatController;
import ru.lab.controller.FlatIn;

public class RemoveById extends Command {
    private long id;
    @Override
    public void execute(FlatController flatController) {
        if (flatController.getFlatById(id) == null) {
            setMessage("Element with this id doesnt exist, or this user doesnt have access to it.\n");
        } else {
            setMessage("Removing element: " + flatController.getFlatById(id) + "\n");
            flatController.removeById(id);
        }
    }

    @Override
    public boolean commandValid() {
        try {
            id = Long.parseLong(getValue());
            CommandsHistory.addCommand("remove_by_id");
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Id must be a positive number");
            return false;
        }
    }
}
