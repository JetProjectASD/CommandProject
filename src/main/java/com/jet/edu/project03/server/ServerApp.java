package com.jet.edu.project03.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server control
 */
/**
 * Server
 */
public class ServerApp {
    private final ServerSocket serverSocket;
    private final List<User> users = new LinkedList<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(50);


    /**
     * Start port and install port and IP address
     */
    public static void main(String[] args) {
        try {
            ServerApp server = new ServerApp(40001);
            System.out.println("Server start...");
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor which instarr server
     */
    public ServerApp(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Start thread which listen port
     */
    public void start() {
        pool.submit(new Acceptor(pool, serverSocket, users));
    }

}
