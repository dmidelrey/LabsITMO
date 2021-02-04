package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.controller.FlatController;
import ru.lab.controller.FlatIn;

public class Add extends Command {
    private Flat flat;

    @Override
    public void execute(FlatController flatController) {
        flatController.addFlat(flat);
        setMessage("Element added to collection");
    }

    @Override
    public boolean commandValid() {
        flat = FlatIn.readFlat();
        CommandsHistory.addCommand("add");
        return true;
    }
}
