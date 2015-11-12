package com.jet.edu.project03.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ServerSender implements Runnable{

    public ServerSender(Socket clientSocket) {
    }

    /**
     * Send mess for server
     */
    public String sendMessage(BufferedReader reader, BufferedWriter writer, String message) throws IOException {
        writeMessage(message, writer);
        return getResponse(reader);
    }


    @Override
    public void run() {

    }

    private String getResponse(BufferedReader reader) throws IOException {
        String result;
        while (true) {
            if (!reader.ready()) {
                if ((result = reader.readLine()) != null)
                    break;
            }
        }
        return result;
    }

    private void writeMessage(String message, BufferedWriter writer) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

}
