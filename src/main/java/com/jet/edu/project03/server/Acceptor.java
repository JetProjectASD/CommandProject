package com.jet.edu.project03.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * устанавливает соединение с клиентом
 */
public class Acceptor implements Runnable {
    private ServerSocket serverSocket;
    private Map<String, Socket> nameToSocketMap;
    private List<Socket> sockets;

    public Acceptor(ServerSocket serverSocket, List<Socket> socketList, Map<String, Socket> nameToSocketMap) {
        this.serverSocket = serverSocket;
        this.sockets = socketList;
        this.nameToSocketMap = nameToSocketMap;
    }

    /**
     * запускае слушатель сокета
     */
    @Override
    public void run() {
        Socket socket = null;
        try {
            System.out.println("Wait for client connect...");
            socket = serverSocket.accept();
            System.out.println("Client connect...");
            sockets.add(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new ClientWorker(socket, sockets, nameToSocketMap));
    }
}
