package com.jet.edu.project03.client;

import com.jet.edu.project03.exeptions.NameExeption;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Listen consoe and send messages
 */
public class Messenger {

    BufferedWriter writer;
    ServerConnector connector = new ServerConnector("127.0.0.1", 40000);


    boolean connectFlag;
    FilterName filter = new FilterName(connector);


    /**
     * constructor which contains stream writer
     */
    public Messenger() {
        System.out.println("Введите имя начиная с команды : /chid ");
    }

    /**
     * Listen console and get mesasge
     */
    public String listen() throws NameExeption, IOException {
        BufferedReader readder = new BufferedReader(new InputStreamReader(System.in));
        String messange = readder.readLine();
        String result = filter.filter(messange, connectFlag);
        return result;
    }

    /**
     * Start client
     */
    public void start() throws NameExeption, IOException {

        String listen = listen();
        if (!listen.equals("ERR")) {
            sendMessege(listen);
        }

    }


    private void sendMessege(String name) throws IOException {
        if (connector.connectToSever(name)) {
            System.out.println("все хорошо");
        }
        System.out.println("конец");

    }
}
