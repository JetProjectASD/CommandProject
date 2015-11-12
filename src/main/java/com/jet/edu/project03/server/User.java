package com.jet.edu.project03.server;

public class User {
    private String name;
    private String room;
    private String message;

    public User(String name, String room, String message) {
        this.name = name;
        this.room = room;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getMessage() {
        return message;
    }
}
