package com.jet.edu.project03.clients.reader;

/**
 * Start client reader
 */
public class ClientReader {

    /**
     * Start client console for reading all chat messages
     */
    public static void main(String[] args) {
        ReadMessenger messenger = new ReadMessenger(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        messenger.start();
    }
}
