package com.jet.edu.project03.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Acceptor implements Runnable {
    private ServerSocket serverSocket;
    private Map<String, Socket> nameToSocketMap = new HashMap<>();
    private List<Socket> sockets;

    public Acceptor(ServerSocket serverSocket, List<Socket> socketList, Map<String, Socket> nameToSocketMap) {
        this.serverSocket = serverSocket;
        this.sockets = socketList;
        this.nameToSocketMap = nameToSocketMap;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new ClientWorker(socket, sockets, nameToSocketMap));
    }
}
