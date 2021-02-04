package ru.lab.controller;

import ru.lab.basic.Flat;
import ru.lab.basic.House;
import ru.lab.basic.Transport;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FlatController {
    private JsonIO jsonIO;
    private java.util.Date initializationTime;
    private String collectionType;
    private Set<Flat> flatSet;

    public void setFlatSet(Set<Flat> flatSet) {
        this.flatSet = Collections.synchronizedSet(flatSet);
    }

    public FlatController() {
        this.flatSet = Collections.synchronizedSet(new LinkedHashSet<>());
        this.collectionType = "LinkedHashSet";
        this.initializationTime = new Date();
    }

    public Flat getFlatById(long id) {
        for (Flat flat : flatSet) {
            if (flat.getId() == id) return flat;
        }
        return null;
    }

    public void addFlat(Flat flat) {
        flatSet.add(flat);
    }

    public void updateFlat(Flat flat) {
        removeById(flat.getId());
        flatSet.add(flat);
    }

    public void removeById(long id) {
        flatSet.remove(getFlatById(id));
    }

    public void clearFlatSet() {
        flatSet.clear();
    }

    public Flat getMinFlat() {
        return flatSet.stream()
                .min(Flat::compareTo)
                .orElse(null);
    }

    public void removeLower(Flat flat) {
        LinkedHashSet<Flat> newFlatSet = new LinkedHashSet<>();
        flatSet.stream()
                .filter(o -> o.compareTo(flat) < 0)
                .forEach(newFlatSet::add);
        setFlatSet(newFlatSet);
    }

    public void removeAnyByHouse(House house) {
        for (Flat flat : flatSet) {
            if (flat.getHouse().equals(house)) {
                removeById(flat.getId());
                return;
            }
        }
    }

    public LinkedHashSet<Flat> filterLessThanTransport(Transport transport) {
        LinkedHashSet<Flat> result = new LinkedHashSet<>();
        flatSet.stream()
                .filter(o -> o.getTransport().compareTo(transport) < 0)
                .forEach(result::add);
        return result;
    }

    public LinkedHashSet<Flat> getDescendingNumberOfRooms() {
        LinkedHashSet<Flat> newFlatSet = new LinkedHashSet<>();
        flatSet.stream()
                .sorted(Flat::compareByNumberOfRooms)
                .forEach(newFlatSet::add);
        return newFlatSet;
    }

    public void saveToFile() {
        System.out.println("Printing to file. . .");
        jsonIO.printCollectionToFile(this.flatSet);
    }

    public String toString() {
        return "Тип коллекции: " + collectionType +
                ", дата инициализации: " + initializationTime +
                ", количество элементов: " + flatSet.size();
    }

    public Set<Flat> getFlatSet() {
        return flatSet;
    }

    public boolean isEmpty() {
        return flatSet.isEmpty();
    }

}
