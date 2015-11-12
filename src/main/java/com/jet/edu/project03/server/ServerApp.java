package com.jet.edu.project03.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerApp {

    private ServerSocket serverSocket;
    private List<Socket> sockets = new LinkedList<>();
    private Map<Socket, User> socketToUserMap = new HashMap<>();


    /**
     * стартует порт и потоки запускает
     */
    public static void main(String[] args) {
        try {
            ServerApp server = new ServerApp(40001);
            System.out.println("Server start...");
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerApp(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        new Thread(new Acceptor()).start();
    }

    private class Acceptor implements Runnable {

        @Override
        public void run() {
            while (true) {
                serverPortListener();
            }
        }

        private void serverPortListener() {
            try {
                System.out.println("Wait for client connect...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connect...");
                sockets.add(socket);
                new Thread(new ClientWorker(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientWorker implements Runnable {
        Socket clientSocket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;

        public ClientWorker(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                while (true) {
                    String message = readStringFromServer(bufferedReader);
                    sendToAllClients(message);
                }
            } catch (IOException ex) {

            }
        }

    }
    private String readStringFromServer(BufferedReader reader) throws IOException {
        while (true) {
            String result;
            if (reader.ready() && (result = reader.readLine()) != null) {
                return result;
            }
        }
    }

    private synchronized void sendToAllClients(String message) {
        try {
            for (Socket socket : sockets) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write(message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
