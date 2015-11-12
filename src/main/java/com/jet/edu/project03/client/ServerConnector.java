package com.jet.edu.project03.client;

import java.io.*;
import java.net.Socket;

public class ServerConnector {

    private String hostname;
    private int port;
    private ServerSender serverSender;
    private Socket clientSocket;

    /**
     * Constructor
     * @param hostname IP address
     */
    public ServerConnector(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        serverSender = new ServerSender();
    }

    /**
     * install connection with server
     */
    public boolean connectToSever(String name) throws IOException {
            clientSocket = new Socket(hostname, port);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String mesFromServer = serverSender.sendMessage(reader, writer, name);
            if ("TRUE".equals(mesFromServer)) {
                    ServerListener serverListener = new ServerListener(reader);
                    Thread listener = new Thread(serverListener);
                    listener.start();
                    return true;
                } else {
                return false;
            }
        }
    }


}
