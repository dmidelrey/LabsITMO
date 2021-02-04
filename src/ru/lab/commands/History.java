package ru.lab.commands;

import ru.lab.controller.FlatController;

public class History extends Command {
    @Override
    public void execute(FlatController flatController) {
    }

    @Override
    public boolean commandValid() {
        setMessage(CommandsHistory.getHistory());
        CommandsHistory.addCommand("history");
        return false;
    }
}
