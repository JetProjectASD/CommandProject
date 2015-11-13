package com.jet.edu.project03.clients.write;

/**
 * Start client writer
 */
public class ClientWriter {
    /**
     * Start client writer and install port
     */
    public static void main(String[] args) {
        //ПРОВЕРКИ АРГУМЕНТОВ!!!
        int port = 40001;
        WriteMessager messenger = new WriteMessager("127.0.0.1", port);
        messenger.start();
    }
}
