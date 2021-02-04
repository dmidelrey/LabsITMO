package ru.lab.basic;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Long x; //Максимальное значение поля: 499, Поле не может быть null
    private int y; //Максимальное значение поля: 423

    public Long getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates(Long x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}