package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.controller.FlatController;

public class Show extends Command{
    @Override
    public void execute(FlatController flatController) {
        if (flatController.isEmpty()) {
            setMessage("Collection is empty\n");
        } else {
            setMessage("");
            for (Flat flat : flatController.getFlatSet()) {
                updateMessage(flat.toString() + "\n");
            }
        }
    }

    @Override
    public boolean commandValid() {
        CommandsHistory.addCommand("show");
        return true;
    }
}
