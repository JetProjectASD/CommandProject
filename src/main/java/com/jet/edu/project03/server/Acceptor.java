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

    Logger logger = Logger.getLogger(Acceptor.class.getName());
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
            socket = serverSocket.accept();
            logger.log(Level.INFO, "Client connected");
            if (socket != null) {
                User client = new User(socket);
                synchronized (users) {
                    users.add(client);
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Can`t connect with client", e);
        }
    }

}