package com.jet.edu.project03.client;

import java.io.BufferedReader;

public class ServerListener implements Runnable {


    private String hostname;
    private int port;
    private BufferedReader reader;

    public ServerListener(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public ServerListener(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        System.out.println(reader);
    }
}
