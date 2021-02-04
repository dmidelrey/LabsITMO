package ru.lab;

import ru.lab.basic.ClientInfo;
import ru.lab.client.Client;
import ru.lab.dbaccess.DBController;
import ru.lab.server.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner  = new Scanner(System.in);
        System.out.println("Enter running mode, server or client");
        String mode = scanner.nextLine();
        if (mode.equals("create")) {
            DBController.createTable();
            System.exit(0);
        }
        if (mode.equals("clear")) {
            DBController.clearTable();
            System.exit(0);
        }
        if (mode.equals("server")) {
            System.out.println("Enter server port");
            try {
                int port = Integer.parseInt(scanner.nextLine());
                Server.run(port);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port");
                System.exit(0);
            }
        } else if (mode.equals("client")){
            try {
                System.out.println("Enter server port");
                int port = Integer.parseInt(scanner.nextLine());
                ClientInfo.setPort(port);
                System.out.println("Enter hostname");
                String host = scanner.nextLine().trim();
                InetAddress address = InetAddress.getByName(host);
                ClientInfo.setHost(host);
                Client.run();
            } catch (NumberFormatException e){
                System.out.println("Invalid port");
                System.exit(0);
            } catch(UnknownHostException e){
                System.out.println("IP for this host not found");
                System.exit(0);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
