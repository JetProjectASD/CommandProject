package com.jet.edu.project03.clients;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Helper class for comfortable printing and writing messages to console
 */
public class UtilitiesMessaging {
    /**
     * return message if user write something to console
     */
    public static String takeMessage(BufferedReader reader) throws IOException {
        while (true) {
            String result;
            if (reader.ready() && (result = reader.readLine()) != null) {
                return result;
            }
        }
    }

    /**
     * Write messages to console
     */
    public static void sendMessage(BufferedWriter writer, String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }
}
