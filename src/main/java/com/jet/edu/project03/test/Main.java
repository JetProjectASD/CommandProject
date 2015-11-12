package com.jet.edu.project03.test;

public class Main {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[1]);
        Messenger messenger = new Messenger(args[0], port);
        messenger.start();
    }
}
