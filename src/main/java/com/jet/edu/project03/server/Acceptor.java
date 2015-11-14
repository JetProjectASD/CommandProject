package com.jet.edu.project03.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Start new thread and listen server socket by blocking accept method
 */
public class Acceptor implements Runnable {
    Logger logger = Logger.getLogger(ServerApp.class.getName());
    private final List<User> users;
    private ServerSocket serverSocket;
    public Acceptor(ServerSocket serverSocket, List<User> users) {
        this.serverSocket = serverSocket;
        this.users = users;
    }

    /**
     * All time listen port for connection clients
     */
    @Override
    public void run() {
        ServerApp.pool.submit(new SocketStreamListener(users));
        while (!Thread.currentThread().interrupted()) {
            listenServerPort();

        }
    }

    private void listenServerPort() {
        try {
            logger.log(Level.INFO, "Wait client connection...");
            Socket socket = null;
            try {
                Thread.currentThread().sleep(10);
                 socket = serverSocket.accept();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.log(Level.INFO, "Client connected");
            if(socket != null) {
                User client = new User(socket);
                synchronized (users) {
                    users.add(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
