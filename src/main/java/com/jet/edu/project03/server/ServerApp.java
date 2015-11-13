package com.jet.edu.project03.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server control
 */
import static com.jet.edu.project03.clients.UtilitiesMessaging.takeMessage;

/**
 * Server
 */
public class ServerApp {

    private final ServerSocket serverSocket;
    private final List<User> users = new LinkedList<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(50);
    private final List<String> messageBuffer = new LinkedList<>();
    private final static String FILE_NAME = "chatHistory.txt";
    private final static String ENCODING = "UTF-8";
    private Printer historyFileLogger;
    private long pseudoUserId = 0L;

    /**
     * Start port and install port and IP address
     */
    public static void main(String[] args) {
        try {
            ServerApp server = new ServerApp(40001);
            System.out.println("Server start...");
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor which instarr server
     */
    public ServerApp(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Start thread which listen port
     */
    public void start() {
        pool.submit(new Acceptor());
        historyFileLogger = new Printer(FILE_NAME, ENCODING);
    }

    private String readStringFromClient(User user, BufferedReader reader, BufferedWriter writer) throws IOException {
        while (true) {
            String result = takeMessage(reader);
//            if ((result = takeMessage(reader)) != null) {
                if (result.equals("CONNECT")) {
                    sendToOneClient(++pseudoUserId + "", writer);
                    sendToOneClient("OK", writer);
                    user.setId(pseudoUserId);
                    return "";
                }
                if (result.equals("READER USER_ID")) {
                    result = takeMessage(reader);
                    long id = Long.parseLong(result);
                    synchronized (users) {
                        users.remove(user);
                        for(User user_ : users) {
                            if(user_.getId() == id) {
                                sendToOneClient("OK", new BufferedWriter(new OutputStreamWriter(user_.getUserOutputStream())));
                                user_.setUserOutputStream(user.getUserOutputStream());
                                System.out.println("user id set");
                                System.out.println(user_.getId());
                                return "";
                            }
                        }
                    }
                }
                if (result.contains("NAME")) {
                    System.out.println("name block start");
                    result = takeMessage(reader);
                    System.out.println(result);
                    synchronized (users) {
                        for(User user_ : users) {
                            if(user_.getName().equals(result)) {
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
                    }
                }
                if (result.equals("SEND")) {
                    result = takeMessage(reader);
                    return result;
                }
                return result;
            }
//        }
    }

    private synchronized void sendBuffer() {
        StringBuilder builder = new StringBuilder();
        for(String string : messageBuffer) {
            builder.append(string).append(System.lineSeparator());
        }
        messageBuffer.clear();
        historyFileLogger.print(builder.toString());
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

    private class Acceptor implements Runnable {

        /**
         * All time listen port for connection clients
         */
        @Override
        public void run() {
            while (true) {
                serverPortListener();
            }
        }

        private void serverPortListener() {
            try {
                System.out.println("Wait for client connect...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connect...");
                User client = new User(socket);
                synchronized (users) {
                    users.add(client);
                }
                pool.submit(new SocketStreamListener());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientWorker implements Runnable {
        private User user;
        private InputStream inputStream;
        private OutputStream outputStream;

        /**
         * constructor which install worker wuth clients
         */

        public ClientWorker(User user) {
            this.user = user;
            this.inputStream = user.getUserInputStream();
            this.outputStream = user.getUserOutputStream();
        }

        /**
         * All time listen string from client and and send this string to all clients with local data
         */
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
                pool.submit(new BufferSender());
                sendToAllClients(sendString);
                System.out.println("after send all");
            }
        }

        private String getDateTimeFormat(LocalDateTime dateTime) {
            return "[" + dateTime.getHour() + ":" + dateTime.getMinute() +
                    ":" + dateTime.getSecond() + " " + dateTime.getDayOfMonth() +
                    "/" + dateTime.getMonthValue() + "/" + dateTime.getYear() + "]";
        }

    }

    private class SocketStreamListener implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (users) {
                    try {
                        for (User user : users) {
                            InputStream userInputStream = user.getUserInputStream();
                            if (userInputStream.available() != 0 && !user.getUserSocket().isClosed()) {
                                pool.submit(new ClientWorker(user));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private class BufferSender implements Runnable {
        @Override
        public void run() {
            if (messageBuffer.size() > 9) {
                    synchronized (messageBuffer) {
                        sendBuffer();
                    }
            }
        }
    }

}
