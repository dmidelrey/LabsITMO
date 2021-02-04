package ru.lab.basic;

import java.io.Serializable;
import java.util.Objects;

public class House implements Serializable {
    private String name; //Поле может быть null
    private long year; //Значение поля должно быть больше 0
    private Long numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    public House(String name, long year, Long numberOfFlatsOnFloor) {
        this.name = name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public Long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public void setNumberOfFlatsOnFloor(Long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    @Override
    public String toString() {
        return "House{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", numberOfFlatsOnFloor=" + numberOfFlatsOnFloor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;
        House house = (House) o;
        return getYear() == house.getYear() &&
                getName().equals(house.getName()) &&
                getNumberOfFlatsOnFloor().equals(house.getNumberOfFlatsOnFloor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getYear(), getNumberOfFlatsOnFloor());
    }
}
