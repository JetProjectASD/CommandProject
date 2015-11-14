package com.jet.edu.project03.server;

import java.io.*;
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
    private BufferedReader userInputStream;
    private BufferedWriter userOutputStream;

    /**
     * Constructor
     */
    public User(Socket socket) throws IOException {
        this.userSocket = socket;
        this.userInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.userOutputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Get name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get room
     */
    public String getRoom() {
        return room;
    }

    /**
     * set room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Get message
     */
    public String getMessage() {
        return message;
    }

    /**
     * set set Message
     */
    public void setMessage(String message) {
        this.message = message;
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
     * set set ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get user input stream
     */
    public BufferedReader getUserInputStream() {
        return userInputStream;
    }

     /**
      * Set uer input stream
     */
    public void setUserInputStream(BufferedReader userInputStream) {
        this.userInputStream = userInputStream;}

    /**
     * set output stream
     */
    public BufferedWriter getUserOutputStream() {
        return userOutputStream;
    }

    /**
     * set user stream
     */
    public void setUserOutputStream(BufferedWriter userOutputStream) {
        this.userOutputStream = userOutputStream;
    }
}
