package com.jet.edu.project03.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ServerSender {

    public ServerSender() {
    }

    public String sendMessage(BufferedReader reader, BufferedWriter writer, String message) throws IOException {
        writeMessage(message, writer);
        return getResponse(reader);
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
