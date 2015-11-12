package com.jet.edu.project03.client;

import com.jet.edu.project03.exeptions.NameExeption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Listen consoe and send messages
 */
public class Client {

    private ServerConnector connector = new ServerConnector("127.0.0.1", 40001);
    private boolean connectFlag;
    private FilterName filter = new FilterName(connector);


    /**
     * constructor which contains stream writer
     */
    public Client() {
        System.out.println("Введите имя начиная с команды : /chid ");
    }


    private String listen() throws NameExeption, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String message = reader.readLine();
        return filter.filter(message, connectFlag);
    }

    /**
     * Start client
     */
    public void start() throws NameExeption, IOException {

        String listen = listen();
        if (!listen.equals("ERR")) {
            sendMessege(listen);
        }
        System.out.println("Введите номер комнаты начиная с команды : /chroom ");

    }


    private void sendMessege(String name) throws IOException {
        connector.connectToSever(name);
        System.out.println("конец");

    }
}
