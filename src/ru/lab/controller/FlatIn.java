package ru.lab.controller;

import ru.lab.basic.*;
import ru.lab.dbaccess.DBController;

import java.util.ArrayList;
import java.util.Scanner;

public class FlatIn {

    private static Scanner scanner;

    public static Flat readFlat() {
        scanner = new Scanner(System.in);
        return new Flat(0L, readName(), readCoordinates(), readArea(), readNumberOfRooms(), readFurnish(), readView(), readTransport(), readHouse());
    }

    public static House readOneHouse() {
        scanner = new Scanner(System.in);
        return readHouse();
    }

    public static Transport readOneTransport() {
        scanner = new Scanner(System.in);
        return readTransport();
    }

    private static String readName() {
        System.out.println("Enter flat name: ");
        String name = scanner.nextLine();
        while (name.isEmpty()) {
            System.out.println("Name should not be empty.");
            System.out.println("Enter flat name: ");
            name = scanner.nextLine();
        }
        return name;
    }

    private static Coordinates readCoordinates() {
        Long x = null;
        Integer y = null;
        while (x == null) {
            System.out.println("Enter x coordinate, must be a long integer, less than 500: ");
            try {
                x = Long.parseLong(scanner.nextLine());
                if (x > 499) x = null;
            } catch (NumberFormatException e) {
                System.out.println("Coordinate should be a long integer number");
            }
        }
        while (y == null) {
            System.out.println("Enter y coordinate, must be an integer, less than 424: ");
            try {
                y = Integer.parseInt(scanner.nextLine());
                if (y > 423) y = null;
            } catch (NumberFormatException e) {
                System.out.println("Coordinate should be an integer number");
            }
        }
        return new Coordinates(x, y);
    }

    private static Double readArea() {
        Double area = null;
        while (area == null) {
            System.out.println("Enter area, must be a positive double number");
            try {
                area = Double.parseDouble(scanner.nextLine());
                if (area <= 0) area = null;
            } catch (NumberFormatException e) {
                System.out.println("Area should be a double number");
            }
        }
        return area;
    }

    private static long readNumberOfRooms() {
        Long numberOfRooms = null;
        while (numberOfRooms == null) {
            System.out.println("Enter number of rooms, must be a positive long integer number");
            try {
                numberOfRooms = Long.parseLong(scanner.nextLine());
                if (numberOfRooms <= 0) numberOfRooms = null;
            } catch (NumberFormatException e) {
                System.out.println("Number of rooms should be a long integer number");
            }
        }
        return numberOfRooms;
    }

    private static Furnish readFurnish() {
        ArrayList<String> furnishList = Furnish.getArrayList();
        System.out.println("Enter furnish from the list: " + String.join(", ", furnishList) + "(leave empty for null value)");
        String input = scanner.nextLine().toUpperCase();
        if (input.equals("")) return null;
        while (!furnishList.contains(input)) {
            System.out.println("List doesnt contain input value. List: " + String.join(", ", furnishList));
            input = scanner.nextLine().toUpperCase();
            if (input.equals("")) return null;
        }
        return Furnish.valueOf(input);
    }

    private static View readView() {
        ArrayList<String> viewList = View.getArrayList();
        System.out.println("Enter view from the list: " + String.join(", ", viewList));
        String input = scanner.nextLine().toUpperCase();
        while (!viewList.contains(input)) {
            System.out.println("List doesnt contain input value. List: " + String.join(", ", viewList));
            input = scanner.nextLine().toUpperCase();
        }
        return View.valueOf(input);
    }

    private static Transport readTransport() {
        ArrayList<String> transportList = Transport.getArrayList();
        System.out.println("Enter transport from the list: " + String.join(", ", transportList));
        String input = scanner.nextLine().toUpperCase();
        while (!transportList.contains(input)) {
            System.out.println("List doesnt contain input value. List: " + String.join(", ", transportList));
            input = scanner.nextLine().toUpperCase();
        }
        return Transport.valueOf(input);
    }

    private static House readHouse() {
        System.out.println("Enter house name (leave empty for null value): ");
        String name = scanner.nextLine();
        if (name.equals("")) name = null;
        Long year = null;
        while (year == null) {
            System.out.println("Enter year, must be positive long integer number");
            try {
                year = Long.parseLong(scanner.nextLine());
                if (year <= 0) year = null;
            } catch (NumberFormatException e) {
                System.out.println("Year must be a long integer number");
            }
        }
        Long numberOfFlatsOnTheFloor = null;
        while (numberOfFlatsOnTheFloor == null) {
            System.out.println("Enter number of flats on the floor, must be a positive long integer number");
            try {
                numberOfFlatsOnTheFloor = Long.parseLong(scanner.nextLine());
                if (numberOfFlatsOnTheFloor <= 0) numberOfFlatsOnTheFloor = null;
            } catch (NumberFormatException e) {
                System.out.println("Should be a long integer number");
            }
        }
        return new House(name, year, numberOfFlatsOnTheFloor);
    }

}
