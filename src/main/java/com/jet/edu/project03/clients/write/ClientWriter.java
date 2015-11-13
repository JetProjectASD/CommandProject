package com.jet.edu.project03.clients.write;

/**
 * Start client writer
 */
public class ClientWriter {

    /**
     * Start client writer and install port
     */
    public static void main(String[] args) {
        WriteMessager messenger = new WriteMessager(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        messenger.start();
    }
}
