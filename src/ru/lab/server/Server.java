package ru.lab.server;

import ru.lab.basic.ClientInfo;
import ru.lab.commands.Command;
import ru.lab.controller.FlatController;
import ru.lab.dbaccess.DBController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {
    private DatagramSocket serverSocket;
    private FlatController flatController;
    private Scanner scanner;

    private Server() throws SocketException {
        this(ClientInfo.getPort());
    }

    private Server(int port) throws SocketException {
        serverSocket = new DatagramSocket(port);
        flatController = new FlatController();
    }

    class Receiver implements Runnable {
        private final ExecutorService executorRequests;
        private final ExecutorService executorSender;

        public Receiver(ExecutorService executorRequests, ExecutorService executorSender) {
            this.executorRequests = executorRequests;
            this.executorSender = executorSender;
        }

        @Override
        public void run() {
            try{
                byte[] buffer = new byte[65536];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                try {
                    serverSocket.receive(datagramPacket);
                } catch (IOException e) {
                    if (scanner.nextLine().toLowerCase().equals("exit")) {
                        System.exit(0);
                    }
                }
                InetAddress address = datagramPacket.getAddress();
                int port = datagramPacket.getPort();

                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
                Command command = (Command) objectInputStream.readObject();
                objectInputStream.close();

                Runnable task = new Handler(command, address, port, executorSender);
                executorRequests.submit(task);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class Handler implements Runnable {
        private Command command;
        private InetAddress address;
        private int port;
        private ExecutorService executorService;

        public Handler(Command command, InetAddress address, int port, ExecutorService executorService) {
            this.command = command;
            this.address = address;
            this.port = port;
            this.executorService = executorService;
        }

        @Override
        public void run() {
            if (command.getMessage().equals("register") || command.getMessage().equals("login")) {
                command.execute(flatController);
            } else {
                flatController = DBController.getDataByUserId(command.getUser().getId());
                command.execute(flatController);
                DBController.saveDataByUserId(command.getUser().getId(), flatController);
            }
            executorService.submit(new Sender(command, address, port));
        }
    }

    class Sender implements Runnable {
        private Command command;
        private InetAddress address;
        private int port;

        public Sender(Command command, InetAddress address, int port) {
            this.command = command;
            this.address = address;
            this.port = port;
        }

        public void run() {
            try {
                byte[] sendBuf = command.toByteArray();

                DatagramPacket datagramPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
                serverSocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void run() {
        ExecutorService executorSender = Executors.newCachedThreadPool();
        ExecutorService executorReceiver = Executors.newFixedThreadPool(1);
        ExecutorService executorRequests = new ForkJoinPool();
        boolean running = true;
        while (running) {
            try {
                Thread.sleep(5000);
                executorReceiver.submit(new Receiver(executorSender, executorRequests));
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
        serverSocket.close();
    }
    public static void run(int port) throws SocketException {
        Server server = new Server(port);
        server.run();
    }
}
