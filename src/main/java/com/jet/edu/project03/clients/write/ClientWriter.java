package com.jet.edu.project03.clients.write;

public class ClientWriter {
    public static void main(String[] args) {
        int port = 40001;
        WriteMessager messenger = new WriteMessager("127.0.0.1", port);
        messenger.start();
    }
}
