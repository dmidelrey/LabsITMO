package ru.lab.client;

import ru.lab.basic.ClientInfo;
import ru.lab.basic.User;
import ru.lab.commands.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.nio.channels.DatagramChannel;
import java.util.regex.Pattern;

public class Client {
    public static CommandData COMMAND_DATA = new CommandData(false);
    private static Scanner scanner = new Scanner(System.in);

    public static boolean loginUser(User user) throws IOException, ClassNotFoundException {
        Command login = new Login();
        login.setUser(user);
        login = exchangeCommands(login);
        System.out.println(login.getMessage());
        return (login.commandValid());
    }

    public static boolean registerUser(User user) throws IOException, ClassNotFoundException {
        Command register = new Register();
        register.setUser(user);
        register = exchangeCommands(register);
        System.out.println(register.getMessage());
        return (register.commandValid());
    }

    public static Command exchangeCommands(Command command) throws IOException, ClassNotFoundException {
        return exchangeCommands(command, InetAddress.getByName(ClientInfo.getHost()), ClientInfo.getPort());
    }

    public static Command exchangeCommands(Command command, InetAddress address, int port) throws IOException, ClassNotFoundException {
        DatagramChannel clientDatagramChannel = DatagramChannel.open();
        clientDatagramChannel.bind(null);

        byte[] sendBuf = command.toByteArray();
        DatagramPacket datagramPacketSend = new DatagramPacket(sendBuf, sendBuf.length, address, port);
        clientDatagramChannel.socket().send(datagramPacketSend);

        byte[] receiveBuf = new byte[100000];
        DatagramPacket datagramPacket = new DatagramPacket(receiveBuf, receiveBuf.length);
        boolean received = false;
        try {
            clientDatagramChannel.socket().receive(datagramPacket);
            received = true;
        } catch (SocketTimeoutException e) {
            System.out.println("No response from server");
        }

        if (received) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(receiveBuf));
            command = (Command) objectInputStream.readObject();
            objectInputStream.close();
        } else {
            command.setMessage("No response from server");
        }
        clientDatagramChannel.close();
        return command;
    }

    public static void run() throws IOException, ClassNotFoundException {
        System.out.println("You need to register or login\nEnter login or register to do it");
        String input = "";
        boolean inputCorrect = false;
        while (!inputCorrect) {
            input = scanner.nextLine();
            if (input.toLowerCase().equals("login") || input.toLowerCase().equals("register")) {
                inputCorrect = true;
            } else if (input.toLowerCase().equals("exit")) {
                System.exit(0);
            } else {
                System.out.println("Invalid input, please enter login or register");
            }
        }
        String action = input;
        inputCorrect = false;
        input = "";
        while (!inputCorrect) {
            System.out.println("Enter username, please use only english letters and numbers");
            input = scanner.nextLine();
            if (Pattern.matches("^[a-zA-Z0-9]{1,}$", input)){
                inputCorrect = true;
            }
        }
        String username = input;
        inputCorrect = false;
        input = "";
        while (!inputCorrect) {
            System.out.println("Enter password, please use only english letters and numbers");
            input = scanner.nextLine();
            if (Pattern.matches("^[a-zA-Z0-9]{1,}$", input)){
                inputCorrect = true;
            }
        }
        String password = encrypt(input);
        User user = new User(username, password);
        if (action.equals("register")) {
            if (!registerUser(user)) {
                System.exit(0);
            }
        }
        if (action.equals("login")) {
            if (!loginUser(user)) {
                System.exit(0);
            }
        }

        System.out.println("Hello " + username);

        while (true) {
            System.out.println("Enter your command, to get commands list use \"help\" command");
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                COMMAND_DATA.setCommand(input);
                if (COMMAND_DATA.getCommand() != null) {
                    Command command = COMMAND_DATA.getCommand();
                    command.setUser(user);
                    if (command.commandValid()) {
                        if (!command.getMessage().equals("script")) {
                            command = exchangeCommands(command);
                            System.out.println(command.getMessage());
                            if (command.getMessage().contains("Logging out")) {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        }
    }

    private static String encrypt(String password) {
        StringBuilder md5Hex = new StringBuilder("");
        try {
            MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());

            byte[] digest = messageDigest.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            md5Hex = new StringBuilder(bigInteger.toString(16));

            while (md5Hex.length() < 32) {
                md5Hex.insert(0, "0");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Hex.toString();
    }
}
