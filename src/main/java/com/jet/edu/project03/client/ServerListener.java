package com.jet.edu.project03.client;

import java.io.BufferedReader;

public class ServerListener implements Runnable {


    private String hostname;
    private int port;
    private BufferedReader reader;

    /**
     * Constructor for connection with server
     * @param hostname IP address
     */
    public ServerListener(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Constructorfor read messange server
     * @param reader
     */
    public ServerListener(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        System.out.println(reader);
    }
}
