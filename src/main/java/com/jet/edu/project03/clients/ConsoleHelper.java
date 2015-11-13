package com.jet.edu.project03.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helper for quck using console
 */
public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Write message to console
     * @param message from user
     */
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    /**
     * Read messages from console
     * @return message to client
     */
    public static String readString() {
        while (true) {
            try {
                return reader.readLine();
            } catch (IOException e) {
                writeMessage("Произошла ошибка при попытке ввода сообщения. Попробуйте еще раз.");
            }
        }
    }
}
