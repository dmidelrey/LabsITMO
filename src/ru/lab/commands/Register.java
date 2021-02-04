package ru.lab.commands;

import org.postgresql.ssl.DbKeyStoreSocketFactory;
import ru.lab.basic.User;
import ru.lab.controller.FlatController;
import ru.lab.dbaccess.DBController;

public class Register extends Command {
    public Register() {
        setMessage("register");
    }
    @Override
    public void execute(FlatController flatController) {
        User checkUser = DBController.getUser(getUser().getUsername());
        if (checkUser != null) {
            setMessage("This username is already taken");
        } else {
            DBController.createUser(getUser());
            setMessage("Registration complete");
        }
    }

    @Override
    public boolean commandValid() {
        return getMessage().equals("Registration complete");
    }
}
