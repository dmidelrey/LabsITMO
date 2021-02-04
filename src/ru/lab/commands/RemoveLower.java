package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.controller.FlatController;
import ru.lab.controller.FlatIn;

public class RemoveLower extends Command {
    private Flat flat;
    @Override
    public void execute(FlatController flatController) {
        flatController.removeLower(flat);
        setMessage("Elements lower than " + flat.toString() + " removed\n");
    }

    @Override
    public boolean commandValid() {
        flat = FlatIn.readFlat();
        CommandsHistory.addCommand("remove_lower");
        return true;
    }
}
