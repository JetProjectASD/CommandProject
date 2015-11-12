package com.jet.edu.project03.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerApp {

    private ServerSocket serverSocket;
    private List<Socket> socketList;
    private Map<String, Socket> nameToSocketMap = new HashMap<>();

    public ServerApp(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }

    private void start() {
        new Thread(new Acceptor(serverSocket, socketList, nameToSocketMap));
    }
}
