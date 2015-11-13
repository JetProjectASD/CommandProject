package com.jet.edu.project03.clients.reader;

import com.jet.edu.project03.clients.ConsoleHelper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.jet.edu.project03.clients.UtilitiesMessaging.*;

public class ReadMessenger extends Thread {
    private String host;
    private int port;
    private long id;

    public ReadMessenger(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try(ServerSocket readerServerSocket = new ServerSocket(45000);
            Socket socket = readerServerSocket.accept()) {
            id = Long.parseLong(takeMessage(new BufferedReader(new InputStreamReader(socket.getInputStream()))));
            System.out.println("id get");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            sendMessage(writer, "READER USER_ID");
            sendMessage(writer, String.valueOf(id));
            System.out.println("id send to server");
            System.out.println(id);

            if ((takeMessage(reader).equals("OK"))) {
                while (true) {
                    try {
                        ConsoleHelper.writeMessage(takeMessage(reader));
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
