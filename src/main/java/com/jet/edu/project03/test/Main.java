package com.jet.edu.project03.test;

public class Main {

    public static void main(String[] args) {
        int port = 40001;
        Messenger messenger = new Messenger("127.0.0.1", port);
        messenger.start();
    }
}
