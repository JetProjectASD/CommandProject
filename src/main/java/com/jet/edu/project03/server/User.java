package com.jet.edu.project03.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * contains all information about user - name, room, message
 */
public class User {
    private String name = "";
    private String room;
    private String message;
    private long id = -1;
    private Socket userSocket;
    private InputStream userInputStream;
    private OutputStream userOutputStream;

    /**
     * Constructor
     */
    public User(Socket socket) throws IOException {
        this.userSocket = socket;
        this.userInputStream = socket.getInputStream();
        this.userOutputStream = socket.getOutputStream();
    }

    /**
     * Get name
     */
    public String getName() {
        return name;
    }

    /**
     * Get room
     */
    public String getRoom() {
        return room;
    }

    /**
     * Get message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get user socet
     */
    public Socket getUserSocket() {
        return userSocket;
    }

    /**
     * Get ID
     */
    public long getId() {
        return id;
    }

    /**
     * Get user input stream
     */
    public InputStream getUserInputStream() {
        return userInputStream;
    }

    /**
     * Set uer input stream
     */
    public void setUserInputStream(InputStream userInputStream) {
        this.userInputStream = userInputStream;
    }

    /**
     * set user stream
     */
    public void setUserOutputStream(OutputStream userOutputStream) {
        this.userOutputStream = userOutputStream;
    }


    /**
     * set output stream
     */
    public OutputStream getUserOutputStream() {
        return userOutputStream;
    }
    /**
     * set name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * set room
     */
    public void setRoom(String room) {
        this.room = room;
    }
    /**
     * set set Message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * set set ID
     */
    public void setId(long id) {
        this.id = id;
    }


}
