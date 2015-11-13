package com.jet.edu.project03.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class User {
    private String name = "";
    private String room;
    private String message;
    private long id = -1;
    private Socket userSocket;
    private InputStream userInputStream;
    private OutputStream userOutputStream;

    public User(Socket socket) throws IOException {
        this.userSocket = socket;
        this.userInputStream = socket.getInputStream();
        this.userOutputStream = socket.getOutputStream();
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

    public Socket getUserSocket() {
        return userSocket;
    }

    public long getId() {
        return id;
    }

    public InputStream getUserInputStream() {
        return userInputStream;
    }

    public void setUserInputStream(InputStream userInputStream) {
        this.userInputStream = userInputStream;
    }

    public OutputStream getUserOutputStream() {
        return userOutputStream;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(long id) {
        this.id = id;
    }
}
