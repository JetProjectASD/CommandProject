package com.jet.edu.project03.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerApp {

    private ServerSocket serverSocket;
    private List<Socket> socketList = new LinkedList<>();
    private Map<String, Socket> nameToSocketMap = new HashMap<>();

    /**
     * стартует порт и потоки запускает
     */

    public static void main(String[] args) {
        try {
            ServerApp server = new ServerApp(40000);
            System.out.println("Server start...");
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ServerApp(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        new Thread(new Acceptor(serverSocket, socketList, nameToSocketMap)).start();
    }
}
