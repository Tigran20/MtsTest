package com.example.system.testmts.model;

import java.io.Serializable;

public class Cow implements Serializable {

    private int id;
    private String breed;
    private String suit;
    private int age;

    public Cow(int id, String breed, String suit, int age) {
        this.id = id;
        this.breed = breed;
        this.suit = suit;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getBreed() {
        return breed;
    }

    public String getSuit() {
        return suit;
    }

    public int getAge() {
        return age;
    }
}
