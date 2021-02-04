package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.controller.FlatController;
import ru.lab.controller.FlatIn;

public class AddIfMin extends Command{
    private Flat flat;
    @Override
    public void execute(FlatController flatController) {
        Flat minFlat = flatController.getMinFlat();
        if (minFlat == null || flat.compareTo(minFlat) < 0) {
            flatController.addFlat(flat);
            setMessage("Element added to collection");
        } else {
            setMessage("There are elements in this users collection less than the input element");
        }
    }

    @Override
    public boolean commandValid() {
        flat = FlatIn.readFlat();
        CommandsHistory.addCommand("add_if_min");
        return true;
    }
}
