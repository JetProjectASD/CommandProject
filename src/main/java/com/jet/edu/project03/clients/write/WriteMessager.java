package com.jet.edu.project03.clients.write;

import com.jet.edu.project03.clients.ConsoleHelper;

import java.io.*;
import java.net.Socket;

public class WriteMessager extends Thread {

    private final String host;
    private final int port;

    public WriteMessager(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket clientSocket = new Socket(host, port);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String str;
            while (!(str = ConsoleHelper.readString()).equals("exit")) {
                writer.write(str);
                writer.newLine();
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
