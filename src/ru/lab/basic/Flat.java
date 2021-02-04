package ru.lab.basic;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Flat implements Comparable<Flat>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double area; //Значение поля должно быть больше 0
    private long numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле может быть null

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    private View view; //Поле не может быть null
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть null

    public Flat(Long id, String name, Coordinates coordinates, Double area, long numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Double getArea() {
        return area;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public View getView() {
        return view;
    }

    public Transport getTransport() {
        return transport;
    }

    public House getHouse() {
        return house;
    }

    public int compareByNumberOfRooms(Flat flat2) {
        return numberOfRooms > flat2.getNumberOfRooms() ? -1 : (numberOfRooms == flat2.numberOfRooms ? 0 : 1);
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", area=" + area +
                ", numberOfRooms=" + numberOfRooms +
                ", furnish=" + furnish +
                ", view=" + view +
                ", transport=" + transport +
                ", house=" + house +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flat)) return false;
        Flat flat = (Flat) o;
        return getNumberOfRooms() == flat.getNumberOfRooms() &&
                Objects.equals(getId(), flat.getId()) &&
                Objects.equals(getName(), flat.getName()) &&
                Objects.equals(getCoordinates(), flat.getCoordinates()) &&
                Objects.equals(getCreationDate(), flat.getCreationDate()) &&
                Objects.equals(getArea(), flat.getArea()) &&
                getFurnish() == flat.getFurnish() &&
                getView() == flat.getView() &&
                getTransport() == flat.getTransport() &&
                Objects.equals(getHouse(), flat.getHouse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCoordinates(), getCreationDate(), getArea(), getNumberOfRooms(), getFurnish(), getView(), getTransport(), getHouse());
    }

    @Override
    public int compareTo(Flat flat1) {
        return this.getName().compareTo(flat1.getName());
    }
}
