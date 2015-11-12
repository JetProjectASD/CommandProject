package com.jet.edu.project03.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;

/**
 * Listen consoe and send messages
 */
public class Messanger {

    BufferedWriter writer;
    ServerConnector connector;

    boolean connectFlag;
    FilterName filter = new FilterName(connector);

    /**
     * constructor which contains stream writer
     */
    public Messanger(BufferedWriter writer) {
        this.writer = writer;
        System.out.println("Введите имя начиная с команды : /chid ");
    }

    /**
     * Listen console and get mesasge
     */
    public void listen() throws NameExeption {
        BufferedReader readder = new BufferedReader(new InputStreamReader(System.in));
        String messange = readder.toString();
        String result = filter.filter(messange, connectFlag);
    }







}
