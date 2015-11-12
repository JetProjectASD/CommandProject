package com.jet.edu.project03.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ClientWorker implements Runnable {
    private Socket clientSocket;
    private List<Socket> sockets;
    private Map<String, Socket> nameToSocketMap;

    public ClientWorker(Socket socket, List<Socket> socketList, Map<String, Socket> nameToSocket) {
        this.clientSocket = socket;
        this.sockets = socketList;
        this.nameToSocketMap = nameToSocket;
    }

    @Override
    public void run() {
        try {
            if(!sockets.contains(clientSocket)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String name = "";
                while (bufferedReader.ready() && (name = bufferedReader.readLine()) != null) {
                }
                if(nameToSocketMap.get(name).equals(clientSocket)) {
                    sendMessageOnClient("TRUE. Connect create", clientSocket);
                    nameToSocketMap.put(name, clientSocket);
                    sockets.add(clientSocket);
                } else {
                    sendMessageOnClient("FALSE. Didnt connect to server. Please write unqiue name", clientSocket);
                    clientSocket.close();
                }
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
