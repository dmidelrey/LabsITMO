package ru.lab.commands;

import ru.lab.controller.FlatController;

public class Info extends Command{

    @Override
    public void execute(FlatController flatController) {
        setMessage(flatController.toString() + "\n");
    }

    @Override
    public boolean commandValid() {
        CommandsHistory.addCommand("info");
        return true;
    }
}
