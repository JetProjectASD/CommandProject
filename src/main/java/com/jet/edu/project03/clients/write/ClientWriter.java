package com.jet.edu.project03.clients.write;

import java.io.*;
import java.net.Socket;

import static com.jet.edu.project03.clients.UtilitiesMessaging.sendMessage;
import static com.jet.edu.project03.clients.UtilitiesMessaging.takeMessage;

/**
 * Start client writer
 */
public class ClientWriter extends Thread {

    private final String host;
    private final int port;
    private final int localPort;
    /**
     * Constructor which install IP address and port
     */
    public ClientWriter(String host, int port, int localPort) {
        this.host = host;
        this.port = port;
        this.localPort = localPort;
    }

    /**
     * Start client writer and install port
     */
    public static void main(String[] args) {
        ClientWriter clientWriter = new ClientWriter(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        clientWriter.start();
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
                WriterMessenger writerMessenger = new WriterMessenger();
                writerMessenger.readConsole(writer);
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

}
