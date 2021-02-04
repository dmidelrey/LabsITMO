package ru.lab.commands;

import ru.lab.basic.User;
import ru.lab.controller.FlatController;
import ru.lab.dbaccess.DBController;

public class Login extends Command{

    public Login() {
        setMessage("login");
    }

    @Override
    public void execute(FlatController flatController) {
        User checkUser = DBController.getUser(this.getUser().getUsername());
        if (checkUser == null) {
            setMessage("Username doesnt exist");
        } else if (!checkUser.getPassword().equals(getUser().getPassword())) {
            setMessage("Invalid password");
        } else {
            setMessage("Login complete");
        }
    }

    @Override
    public boolean commandValid() {
        return this.getMessage().equals("Login complete");
    }
}
