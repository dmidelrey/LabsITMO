package ru.lab.commands;

import ru.lab.basic.Flat;
import ru.lab.basic.Transport;
import ru.lab.controller.FlatController;
import ru.lab.controller.FlatIn;

public class FilterLessThanTransport extends Command{

    private Transport transport;
    @Override
    public void execute(FlatController flatController) {
        if (flatController.isEmpty()) {
            setMessage("Collection is empty\n");
        } else {
            setMessage("");
            for (Flat flat : flatController.filterLessThanTransport(transport)) {
                updateMessage(flat.toString() + "\n");
            }
        }
    }

    @Override
    public boolean commandValid() {
        transport = FlatIn.readOneTransport();
        CommandsHistory.addCommand("filter_less_than_transport");
        return true;
    }
}
