package ru.lab.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.lab.basic.*;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class JsonIO {
    private Scanner scanner;
    private PrintWriter printWriter;

    public JsonIO(String inPath, String outPath) {
        try {
            scanner = new Scanner(new FileReader(inPath));
            this.printWriter = new PrintWriter(new FileWriter(outPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashSet<Flat> readCollectionFromFile() {
        return parseString(getStringFromFile());
    }

    private Flat getFlatFromJSONObject(JSONObject object, Long id) {
        JSONObject flatObject = (JSONObject) object.get("Flat");
        String name = (String) flatObject.get("name");
        JSONObject coordinatesObject = (JSONObject) flatObject.get("coordinates");
        Long x = (Long)coordinatesObject.get("x");
        int y = ((Long) coordinatesObject.get("y")).intValue();
        Coordinates coordinates = new Coordinates(x, y);
        Double area = (Double) flatObject.get("area");
        long numberOfRooms = (long) flatObject.get("numberOfRooms");
        Furnish furnish = Furnish.valueOf((String) flatObject.get("furnish"));
        View view = View.valueOf((String) flatObject.get("view"));
        Transport transport = Transport.valueOf((String) flatObject.get("transport"));
        JSONObject houseObject = (JSONObject) flatObject.get("house");
        String houseName = (String)houseObject.get("name");
        long year = (long) houseObject.get("year");
        Long numberOfFlatsOnFloor = (Long) houseObject.get("numberOfFlatsOnFloor");
        House house = new House(houseName, year, numberOfFlatsOnFloor);
        return new Flat(id, name, coordinates, area, numberOfRooms, furnish, view, transport, house);
    }

    private LinkedHashSet<Flat> parseString(String in) {
        LinkedHashSet<Flat> result = new LinkedHashSet<>();
        JSONParser parser = new JSONParser();
        long id = 0L;
        try {
            JSONArray array = (JSONArray) parser.parse(in);
            for (Object o : array) {
                JSONObject object = (JSONObject) o;
                result.add(getFlatFromJSONObject(object, id++));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getStringFromFile() {
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            input.append(scanner.nextLine());
        }
        return input.toString();
    }

    JSONObject jsonObjectFromFlat(Flat flat) {
        JSONObject flatObject = new JSONObject();
        flatObject.put("id", flat.getId());
        flatObject.put("name", flat.getName());
        JSONObject coordinatesObject = new JSONObject();
        coordinatesObject.put("x", flat.getCoordinates().getX());
        coordinatesObject.put("y", flat.getCoordinates().getY());
        flatObject.put("coordinates", coordinatesObject);
        flatObject.put("creationDate", flat.getCreationDate().toString());
        flatObject.put("area", flat.getArea());
        flatObject.put("numberOfRooms", flat.getNumberOfRooms());
        flatObject.put("furnish", flat.getFurnish().toString());
        flatObject.put("view", flat.getView().toString());
        flatObject.put("transport", flat.getTransport().toString());
        JSONObject houseObject = new JSONObject();
        houseObject.put("name", flat.getHouse().getName());
        houseObject.put("year", flat.getHouse().getYear());
        houseObject.put("numberOfFlatsOnFloor", flat.getHouse().getNumberOfFlatsOnFloor());
        flatObject.put("house", houseObject);
        JSONObject object = new JSONObject();
        object.put("Flat", flatObject);
        return object;
    }
    public void printCollectionToFile(Set<Flat> collection) {
        JSONArray collectionArray = new JSONArray();
        for (Flat flat : collection) {
            collectionArray.add(jsonObjectFromFlat(flat));
        }
        printWriter.print(collectionArray.toJSONString());
        printWriter.flush();
    }
}
