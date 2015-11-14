package com.jet.edu.project03.clients.reader;

import com.jet.edu.project03.clients.ConsoleHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import static com.jet.edu.project03.clients.UtilitiesMessaging.*;

/**
 * Read messages from console
 */
public class ReaderMessenger {

    private BufferedReader reader;
    private BufferedWriter writer;

    /**
     * Constructor which install read and write stream
     */
    public ReaderMessenger(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * start reading
     */
    public void startReadingMessageFromServer(long id) throws IOException {
        sendMessage(writer, "READER USER_ID");
        sendMessage(writer, String.valueOf(id));
        if ((takeMessage(reader).equals("OK"))) {
            writeToLocalConsole(reader);
        }
    }

    private void writeToLocalConsole(BufferedReader reader) throws IOException {
        while (true) {
            ConsoleHelper.writeMessage(takeMessage(reader));
        }
    }
}
