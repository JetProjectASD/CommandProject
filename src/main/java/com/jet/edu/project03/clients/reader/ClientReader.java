package com.jet.edu.project03.clients.reader;

public class ClientReader {

    public static void main(String[] args) {
        //ПРОВЕРКИ АРГУМЕНТОВ!!!
        int port = 40001;
        long id = 100500L;
        ReadMessenger messenger = new ReadMessenger("127.0.0.1", port);
        messenger.start();
    }
}
