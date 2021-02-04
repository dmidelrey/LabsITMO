package ru.lab.commands;

import ru.lab.controller.FlatController;

public class Exit extends Command {
    @Override
    public void execute(FlatController flatController) {
        setMessage("Logging out\n");
    }

    @Override
    public boolean commandValid() {
        CommandsHistory.addCommand("exit :)");
        return true;
    }
}
