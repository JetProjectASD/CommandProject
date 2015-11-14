package com.jet.edu.project03.clients.write;

import com.jet.edu.project03.clients.ConsoleHelper;
import com.jet.edu.project03.clients.write.exceptions.SomeException;

import java.io.*;
import java.net.Socket;

import static com.jet.edu.project03.clients.UtilitiesMessaging.sendMessage;
import static com.jet.edu.project03.clients.UtilitiesMessaging.takeMessage;

/**
 * New thread for wtite user messages in console
 */

public class WriteMessager extends Thread {

    private final String host;
    private final int port;
    private final int localPort;

    /**
     * Constructor which install IP address and port
     */
    public WriteMessager(String host, int port, int localPort) {
        this.host = host;
        this.port = port;
        this.localPort = localPort;
    }

    /**
     * Write any messages in the console while user not write "exit" - command for stop client
     */
    @Override
    public void run() {
        try (Socket clientSocket = new Socket(host, port);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            sendMessage(writer, "CONNECT");
            long sessionId = Long.parseLong(takeMessage(reader));
            if ((takeMessage(reader).equals("OK"))) {
                sendIdToReader(sessionId);
                readConsole(writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendIdToReader(long sessionId) throws IOException {
        try (Socket socket = new Socket(host, localPort)) {
            BufferedWriter writeToReader = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            sendMessage(writeToReader, String.valueOf(sessionId));
        }
    }

    private String deletePrefixFromMessage(String prefix, String message) {
        return message.replaceFirst(prefix + " ", "");
    }

    private void sendCommandAndMessage(BufferedWriter writer, String prefix, String message) throws SomeException, IOException {
        message = deletePrefixFromMessage(prefix, message);
        String commandToServer = IDsFiltering.getPrefix(prefix);

        if (!checkName(message, commandToServer) && !checkSend(message, commandToServer)
                && !checkHistory(message, commandToServer) && !checkRoom(message, commandToServer)) {
            throw new SomeException("Incorrect Name.");
        }

        sendMessage(writer, commandToServer);
        sendMessage(writer, message);
        ConsoleHelper.writeMessage(commandToServer + " " + message);
    }

    private boolean checkName(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.NAME.toString()) && message.length() < 50 && !message.contains(" ")
                && !message.isEmpty();
    }

    private boolean checkSend(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.SEND.toString()) && message.length() < 150;
    }

    private boolean checkHistory(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.HISTORY.toString()) && !message.equals("");
    }

    private boolean checkRoom(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.ROOM.toString()) && message.length() < 50;
    }

    private void readConsole(BufferedWriter writer) throws IOException {
        String str;
        while ((str = ConsoleHelper.readString()) != null) {
            String[] split = str.split(" ");
            try {
                if (!split[0].isEmpty()) {
                    sendCommandAndMessage(writer, split[0], str);
                }
            } catch (SomeException e) {
                ConsoleHelper.writeMessage("Not correct input.");
            }
        }
    }
}
