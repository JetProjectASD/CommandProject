package com.jet.edu.project03.server;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ClientWorker implements Runnable {
    private Socket clientSocket;
    private List<Socket> sockets;
    private Map<String, Socket> nameToSocketMap;
    private BufferedReader bufferedReader;

    public ClientWorker(Socket socket, List<Socket> socketList, Map<String, Socket> nameToSocket) {
        this.clientSocket = socket;
        this.sockets = socketList;
        this.nameToSocketMap = nameToSocket;
    }

    @Override
    public void run() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                message += line;
            }
            for(Socket socket : sockets) {
                sendMessageOnClient(System.currentTimeMillis() + ":" + message, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageOnClient(String message, Socket clientSocket) throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeUTF(message);
        objectOutputStream.flush();
    }
}
