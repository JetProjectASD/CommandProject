package com.jet.edu.project03.clients.write;

import com.jet.edu.project03.clients.ConsoleHelper;
import com.jet.edu.project03.clients.write.exceptions.SomeException;

import java.io.BufferedWriter;
import java.io.IOException;

import static com.jet.edu.project03.clients.UtilitiesMessaging.sendMessage;

/**
 * Write message
 */
public class WriterMessenger {
    /**
     * read the console
     */
    public void readConsole(BufferedWriter writer) throws IOException {
        String str;
        while ((str = ConsoleHelper.readString()) != null) {
            String[] split = str.split(" ");
            try {
                if (!split[0].isEmpty()) {
                    sendCommandAndMessage(writer, split[0], str);
                }
            } catch (SomeException e) {
                ConsoleHelper.writeMessage("Not correct input.");
            }
        }
    }

    private String deletePrefixFromMessage(String prefix, String message) {
        if (message.contains(" ")) {
            return message.replaceFirst(prefix + " ", "");
        } else {
            return message.replaceFirst(prefix, "");
        }
    }

    private void sendCommandAndMessage(BufferedWriter writer, String prefix, String message) throws SomeException, IOException {
        message = deletePrefixFromMessage(prefix, message);
        String commandToServer = IDsFiltering.getPrefix(prefix);

        if (!checkName(message, commandToServer) && !checkSend(message, commandToServer)
                && !checkHistory(message, commandToServer) && !checkRoom(message, commandToServer)) {
            throw new SomeException("Incorrect Name.");
        }

        sendMessage(writer, commandToServer);
        sendMessage(writer, message);
        ConsoleHelper.writeMessage(commandToServer + " " + message);
    }

    private boolean checkName(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.NAME.toString()) && message.length() < 50
                && !message.contains(" ") && !message.isEmpty();
    }

    private boolean checkSend(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.SEND.toString()) && message.length() < 150 && !message.isEmpty();
    }

    private boolean checkHistory(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.HISTORY.toString()) && !message.isEmpty() && !message.contains(" ");
    }

    private boolean checkRoom(String message, String commandToServer) {
        return commandToServer.equals(IDsFiltering.ROOM.toString()) && message.length() < 50
                && !message.contains(" ") && !message.isEmpty();
    }
}
