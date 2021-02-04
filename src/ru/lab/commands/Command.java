package ru.lab.commands;

import ru.lab.basic.User;

import java.io.*;

public abstract class Command implements Executable, Valid, Serializable {
    private String value;

    private User user = new User("", "");

    private String message = "";

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void updateMessage(String message) {
        this.message += message;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }
}
