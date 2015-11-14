package com.jet.edu.project03.server;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.jet.edu.project03.clients.UtilitiesMessaging.takeMessage;

/**
 * Class which handle clients commands
 */
public class ClientWorker implements Runnable {

    private Logger logger = Logger.getLogger(ClientWorker.class.getName());
    private BufferedReader inputStream;
    private BufferedWriter outputStream;
    private User user;
    private long pseudoUserId;
    private final List<User> users;

    /**
     * Constructor which install current user, all USERS array, user id
     */
    public ClientWorker(User user, List<User> users, Long pseudoUserId) {
        this.user = user;
        this.users = users;
        this.inputStream = user.getUserInputStream();
        this.outputStream = user.getUserOutputStream();
        this.pseudoUserId = pseudoUserId;
    }

    /**
     * Handle commands
     */
    @Override
    public void run() {
        String message = "";
        try {
            message = readStringFromClient(user, inputStream, outputStream);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Cant get information from client. Possible problems Socket Closed, I/O Closed");
        }
        if (checkUserInfoAndMessageAvailable(message)) {
            LocalDateTime dateTime = LocalDateTime.now();
            String sendString = getDateTimeFormat(dateTime) + " ["+user.getRoom()+"] " + user.getName() + ": " + message;
            sendToAllClients(sendString, user.getRoom());
            addInLastMessagesList(sendString);
            ServerApp.pool.submit(new ChatHistoryPrinter("serverChatLog.txt", "UTF-8"));
        }
    }

    private void addInLastMessagesList(String sendString) {
        synchronized (ServerApp.lastTenChatMessages) {
            ServerApp.lastTenChatMessages.add(sendString);
        }
    }

    private boolean checkUserInfoAndMessageAvailable(String message) {
        return !user.getName().equals("") && user.getId() != -1 && !message.equals("") && !user.getRoom().isEmpty();
    }

    private String readStringFromClient(User user, BufferedReader reader, BufferedWriter writer) throws IOException {
        while (!Thread.currentThread().interrupted()) {
            String result = takeMessage(reader);
            if (result.equals("CONNECT")) {
                sendToOneClient(pseudoUserId + "", writer);
                sendToOneClient("OK", writer);
                user.setId(this.pseudoUserId);
                return "";
            } else if (result.equals("READER USER_ID")) {
                result = takeMessage(reader);
                long id = Long.parseLong(result);
                synchronized (users) {
                    users.remove(user);
                    for(User user_ : users) {
                        if(user_.getId() == id) {
                            sendToOneClient("OK", user.getUserOutputStream());
                            user_.setUserOutputStream(user.getUserOutputStream());
                            return "";
                        }
                    }
                }
            } else if (result.contains("NAME")) {
                result = takeMessage(reader);
                System.out.println(result);
                synchronized (users) {
                    for(User user_ : users) {
                        if(user_.getName().equals(result)) {
                            sendToOneClient("Name is occupied", user.getUserOutputStream());
                            return "";
                        } else {
                            user.setName(result);
                            sendToOneClient("OK", user.getUserOutputStream());
                            return "";
                        }
                    }
                }
            } else if (result.equals("SEND")) {
                result = takeMessage(reader);
                return result;
            } else if (result.equals("HISTORY")) {
                String history = new ChatHistoryPrinter("serverChatLog.txt", "UTF-8").getHistoryMessages();
                sendToOneClient(history, user.getUserOutputStream());
                return "";
            } else if (result.equals("ROOM")) {
                String roomName = takeMessage(reader);
                user.setRoom(roomName);
                return "";
            }
            return result;
        }
        return "";
    }

    private void sendToOneClient(String message, BufferedWriter writer) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private void sendToAllClients(String message, String roomName) {
        System.out.println("send all method start");
        synchronized (users) {
            for (User user : users) {
                if (!user.getName().equals("") && user.getRoom().equals(roomName)) {
                    try {
                        BufferedWriter writer = user.getUserOutputStream();
                        writer.write(message);
                        writer.newLine();
                        writer.flush();
                    } catch (IOException e) {
                        logger.log(Level.WARNING, user.getName() + " cant send message", e);
                    }
                }
            }
        }
    }

    private String getDateTimeFormat(LocalDateTime dateTime) {
        return "[" + dateTime.getHour() + ":" + dateTime.getMinute() +
                ":" + dateTime.getSecond() + " " + dateTime.getDayOfMonth() +
                "/" + dateTime.getMonthValue() + "/" + dateTime.getYear() + "]";
    }

}
