package com.jet.edu.project03.clients.reader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.jet.edu.project03.clients.UtilitiesMessaging.takeMessage;

/**
 * Start client reader
 */
public class ClientReader extends Thread {

    private final int localPort;
    private String host;
    private int port;

    /**
     * Constructor for initialling ip address and port
     *
     * @param host IP address
     */
    public ClientReader(String host, int port, int localPort) {
        this.host = host;
        this.port = port;
        this.localPort = localPort;
    }

    /**
     * Start client console for reading all chat messages
     */
    public static void main(String[] args) {
        ClientReader clientReader = new ClientReader(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        clientReader.start();
    }

    /**
     * All time listen port and write to console all messages from server
     */
    @Override
    public void run() {
        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            ReaderMessenger readerMessenger = new ReaderMessenger(reader, writer);
            long clientId = startLocalServerForSynchronizationLocalClients();
            if (clientId > 0) {
                readerMessenger.startReadingMessageFromServer(clientId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long startLocalServerForSynchronizationLocalClients() throws IOException {
        try (ServerSocket readerServerSocket = new ServerSocket(localPort);
             Socket socket = readerServerSocket.accept()) {
            return Long.parseLong(takeMessage(new BufferedReader(new InputStreamReader(socket.getInputStream()))));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
