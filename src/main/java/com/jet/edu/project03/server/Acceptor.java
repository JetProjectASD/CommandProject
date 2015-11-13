package com.jet.edu.project03.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Acceptor implements Runnable {

    private final List<User> users;
    private ExecutorService pool;
    private ServerSocket serverSocket;

    public Acceptor(ExecutorService pool, ServerSocket serverSocket, List<User> users) {

        this.pool = pool;
        this.serverSocket = serverSocket;
        this.users = users;
    }

    /**
     * All time listen port for connection clients
     */
    @Override
    public void run() {
        pool.submit(new SocketStreamListener(users, pool));
        while (true) {
            listenServerPort();
        }
    }

    private void listenServerPort() {
        try {
            System.out.println("Wait for client connect...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connect...");
            User client = new User(socket);
            synchronized (users) {
                users.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
