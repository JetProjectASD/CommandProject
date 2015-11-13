package com.jet.edu.project03.clients.write;

import com.jet.edu.project03.clients.ConsoleHelper;
import com.jet.edu.project03.clients.write.exceptions.SomeException;

import java.io.*;
import java.net.Socket;

/**
 * New thread for wtite user messages in console
 */
import static com.jet.edu.project03.clients.UtilitiesMessaging.sendMessage;
import static com.jet.edu.project03.clients.UtilitiesMessaging.takeMessage;

public class WriteMessager extends Thread {

    private final String host;
    private final int port;

    /**
     * Constructor which install IP address and port
     */
    public WriteMessager(String host, int port) {
        this.host = host;
        this.port = port;
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
            long sessionId = Long.parseLong(takeMessage(reader)); // Тут нужно как-то обработать, а вдруг придет не лонг оО
            System.out.println("id get");
            System.out.println(sessionId);
            if ((takeMessage(reader).equals("OK"))) {
                try (Socket socket = new Socket("127.0.0.1", 45000)) {
                    BufferedWriter writeToReader = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    sendMessage(writeToReader, String.valueOf(sessionId));
                }
                String str;
                while ((str = ConsoleHelper.readString()) != null) {
                    String[] split = str.split(" ");
                    try {
                        String message;
                        if (!split[0].isEmpty() && (message = deletePrefixFromMessage(split[0], str)).length() < 150) {
                            String commandToServer = IDsFiltering.getPrefix(split[0]);
                            System.out.println(commandToServer);
                            sendMessage(writer, commandToServer);
                            sendMessage(writer, message);
                            System.out.println(commandToServer + " " + message);
                        }
                    } catch (SomeException e) {
                        ConsoleHelper.writeMessage("Ввод не корректен.");
                        ConsoleHelper.writeMessage(e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String deletePrefixFromMessage(String prefix, String message) {
        return message.replaceFirst(prefix, "");
    }
}
