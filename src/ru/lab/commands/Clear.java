package ru.lab.commands;

import ru.lab.controller.FlatController;

public class Clear extends Command {
    @Override
    public void execute(FlatController flatController) {
        flatController.clearFlatSet();
        setMessage("Collection cleared\n");
    }

    @Override
    public boolean commandValid() {
        CommandsHistory.addCommand("clear");
        return true;
    }
}
