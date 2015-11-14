package com.jet.edu.project03.loadingtest;

import com.jet.edu.project03.clients.ConsoleHelper;
import com.jet.edu.project03.clients.UtilitiesMessaging;

import java.io.*;
import java.net.Socket;

public class MockClient extends Thread {

    private String host;
    private int port;
    private String name;
    private String room;
    private String message;

    public MockClient(String host, int port, String name, String room, String message) {
        this.host = host;
        this.port = port;
        this.name = name;
        this.room = room;
        this.message = message;
    }

    @Override
    public void run() {
        try (Socket socketWriter = new Socket(host, port);
             BufferedReader readerForWriter = new BufferedReader(new InputStreamReader(socketWriter.getInputStream()));
             BufferedWriter writerForWriter = new BufferedWriter(new OutputStreamWriter(socketWriter.getOutputStream()));
             Socket socketReader = new Socket(host, port);
             BufferedReader readerForReader = new BufferedReader(new InputStreamReader(socketReader.getInputStream()));
             BufferedWriter writerForReader = new BufferedWriter(new OutputStreamWriter(socketReader.getOutputStream()))) {

            registrationWriter(readerForWriter, writerForWriter, readerForReader, writerForReader);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registrationWriter(BufferedReader readerForWriter, BufferedWriter writerForWriter, BufferedReader readerForReader, BufferedWriter writerForReader) throws IOException {
        UtilitiesMessaging.sendMessage(writerForWriter, "CONNECT");
        String id = UtilitiesMessaging.takeMessage(readerForWriter);
        String okForWriterMessage = UtilitiesMessaging.takeMessage(readerForWriter);

        if ("OK".equals(okForWriterMessage)) {
            registrationReader(writerForWriter, readerForReader, writerForReader, id);
        }
    }

    private void registrationReader(BufferedWriter writerForWriter, BufferedReader readerForReader, BufferedWriter writerForReader, String id) throws IOException {
        UtilitiesMessaging.sendMessage(writerForReader, "READER USER_ID");
        UtilitiesMessaging.sendMessage(writerForReader, id);
        String okForReaderMessage = UtilitiesMessaging.takeMessage(readerForReader);

        if ("OK".equals(okForReaderMessage)) {
            setUserNameForChat(writerForWriter, readerForReader);
        }
    }

    private void setUserNameForChat(BufferedWriter writerForWriter, BufferedReader readerForReader) throws IOException {
        UtilitiesMessaging.sendMessage(writerForWriter, "NAME");
        UtilitiesMessaging.sendMessage(writerForWriter, name);
        String okForName = UtilitiesMessaging.takeMessage(readerForReader);

        if ("OK".equals(okForName)) {
            setRoomForChat(writerForWriter, readerForReader);
        }
    }

    private void setRoomForChat(BufferedWriter writerForWriter, BufferedReader readerForReader) throws IOException {
        UtilitiesMessaging.sendMessage(writerForWriter, "ROOM");
        UtilitiesMessaging.sendMessage(writerForWriter, room);

        writeAndReadMessage(writerForWriter, readerForReader);
    }

    private void writeAndReadMessage(BufferedWriter writerForWriter, BufferedReader readerForReader) throws IOException {
        int count = 0;
        while (true) {
            String messageCurrentClient = message + count;
            UtilitiesMessaging.sendMessage(writerForWriter, "SEND");
            long startTime = System.currentTimeMillis();
            UtilitiesMessaging.sendMessage(writerForWriter, messageCurrentClient);

            long endTime = getTimeReadResponse(readerForReader, messageCurrentClient, startTime);

            ConsoleHelper.writeMessage("user: " + name + "; message: " + messageCurrentClient + "; Response time: " + endTime);
            count++;
        }
    }

    private long getTimeReadResponse(BufferedReader readerForReader, String messageCurrentClient, long startTime) throws IOException {
        while (true) {
            String message = UtilitiesMessaging.takeMessage(readerForReader);
            long endTime = System.currentTimeMillis() - startTime;
            if (message.contains(name) && message.contains(messageCurrentClient)) {
                return endTime;
            }
        }
    }
}
