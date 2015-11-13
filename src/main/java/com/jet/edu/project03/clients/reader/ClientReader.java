package com.jet.edu.project03.clients.reader;

/**
 * Start client reader
 */
public class ClientReader {

    /**
     * Start client console for reading all chat messages
     */
    public static void main(String[] args) {
        int port = 40002;
        ReadMessenger messenger = new ReadMessenger("127.0.0.1", port);
        messenger.start();
    }
}
