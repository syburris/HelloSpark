package com.company;

/**
 * Created by stevenburris on 10/3/16.
 */
public class User {
    String name;


    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
