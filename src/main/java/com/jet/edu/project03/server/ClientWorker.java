package com.jet.edu.project03.server;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.jet.edu.project03.clients.UtilitiesMessaging.takeMessage;

public class ClientWorker implements Runnable {

    private final List<String> messageBuffer = new LinkedList<>();
    private final List<User> users;

    private long pseudoUserId;
    private InputStream inputStream;
    private OutputStream outputStream;
    private User user;
    private ExecutorService pool;

    public ClientWorker(User user, List<User> users, ExecutorService pool, Long pseudoUserId) {
        this.user = user;
        this.users = users;
        this.inputStream = user.getUserInputStream();
        this.outputStream = user.getUserOutputStream();
        this.pool = pool;
        this.pseudoUserId = pseudoUserId;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        String message = "";
        try {
            message = readStringFromClient(user, bufferedReader, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(user.getName() + " " + user.getId() + " " + message);
        if (!user.getName().equals("") && user.getId() != -1 && !message.equals("")) {
            System.out.println("send message " + message);
            LocalDateTime dateTime = LocalDateTime.now();
            String sendString = getDateTimeFormat(dateTime) + " " + user.getName() + ": " + message;
            synchronized (messageBuffer) {
                messageBuffer.add(sendString);
            }
            System.out.println("after sync");
            pool.submit(new BufferSender(messageBuffer));
            sendToAllClients(sendString);
            System.out.println("after send all");
        }
    }

    private String readStringFromClient(User user, BufferedReader reader, BufferedWriter writer) throws IOException {
        while (true) {
            String result = takeMessage(reader);
            if (result.equals("CONNECT")) {
                sendToOneClient(pseudoUserId + "", writer);
                sendToOneClient("OK", writer);
                System.out.println(">>>>>>>>>>>>>>>>>>>>" + pseudoUserId);
                user.setId(this.pseudoUserId);
                return "";
            } else if (result.equals("READER USER_ID")) {
                result = takeMessage(reader);
                System.out.println(result);
                long id = Long.parseLong(result);
                synchronized (users) {
                    users.remove(user);
                    for (User user_ : users) {
                        if (user_.getId() == id) {
                            sendToOneClient("OK", new BufferedWriter(new OutputStreamWriter(user.getUserOutputStream())));
                            user_.setUserOutputStream(user.getUserOutputStream());
                            System.out.println("user id set");
                            System.out.println(user_.getId());
                            return "";
                        }
                    }
                }
            } else if (result.contains("NAME")) {
                System.out.println("name block start");
                result = takeMessage(reader);
                System.out.println(result);
                synchronized (users) {
                    ////////////////////// УЗНАТЬ ПОЧЕМУ РАБОТАЕТ?!//////////////////////////////////////
                    for (User user_ : users) {
                        if (user_.getName().equals(result)) {
                            System.out.println("name non set");
                            sendToOneClient("Name is occupied", new BufferedWriter(new OutputStreamWriter(user_.getUserOutputStream())));
                            return "";
                        } else {
                            user.setName(result);
                            System.out.println("Set user name");
                            sendToOneClient("OK", new BufferedWriter(new OutputStreamWriter(user_.getUserOutputStream())));
                            return "";
                        }
                    }
                    //////////////////////////////////////////////////////////////////////////////////////
                }
            } else if (result.equals("SEND")) {
                result = takeMessage(reader);
                return result;
            }
            return result;
        }
    }

    private void sendToOneClient(String message, BufferedWriter writer) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private void sendToAllClients(String message) {
        System.out.println("send all method start");
        synchronized (users) {
            for (User user : users) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(user.getUserOutputStream()));
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
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
