package ru.lab.commands;

import ru.lab.basic.House;
import ru.lab.controller.FlatController;
import ru.lab.controller.FlatIn;

public class RemoveAnyByHouse extends Command{
    private House house;
    @Override
    public void execute(FlatController flatController) {
        flatController.removeAnyByHouse(house);
        setMessage("Elements with houses like " + house.toString() + " removed\n");
    }

    @Override
    public boolean commandValid() {
        house = FlatIn.readOneHouse();
        CommandsHistory.addCommand("remove_any_by_house");
        return true;
    }
}
