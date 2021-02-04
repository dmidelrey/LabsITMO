package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.controller.FlatController;
import ru.lab.controller.FlatIn;

public class Update extends Command {
    private long id;
    private Flat flat;
    @Override
    public void execute(FlatController flatController) {
        if (flatController.getFlatById(id) == null) {
            setMessage("Element with this id doesnt exist, or this user doesnt have access to it.\n");
        } else {
            flatController.updateFlat(flat);
            setMessage("Element updated\n");
        }
    }

    @Override
    public boolean commandValid() {
        try {
            id = Long.parseLong(getValue());
            flat = FlatIn.readFlat();
            flat.setId(id);
            CommandsHistory.addCommand("update");
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Id must be a positive number");
            return false;
        }
    }
}
