package com.jet.edu.project03.clients.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadMessenger extends Thread {
    private String host;
    private int port;

    public ReadMessenger(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (true) {
                try {
                    System.out.println(readStringFromServer(reader));
                } catch (IOException e) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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
}
