package com.jet.edu.project03.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server control
 */
public class ServerApp {
    private final ServerSocket serverSocket;
    /**
     * Collection of users
     */
    protected static final List<User> USERS = new LinkedList<>();
    /**
     * pool of threads
     */
    public final static ExecutorService pool = Executors.newFixedThreadPool(1000);
    /**
     * Collection of last messages
     */
    public final static List<String> lastTenChatMessages = new LinkedList<>();
    private Logger logger = Logger.getLogger(ServerApp.class.toString());

    /**
     * Start port and install port and IP address
     */
    public static void main(String[] args) {
        ServerApp server = new ServerApp(40001);
        server.start();
    }

    /**
     * Constructor which install server
     */
    public ServerApp(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Unable to open server socket with " + port + " port", e);
            throw new RuntimeException("blabla",e);
        }
    }

    /**
     * Start thread which listen port
     */
    public void start() {
        logger.log(Level.INFO, "Server start successfully");
        pool.submit(new Acceptor(serverSocket));
//        new Thread(() -> {
//            String string = ConsoleHelper.readString();
//            if(string.equals("stop")) {
//                try {
//                    stop();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    /*private void stop() throws IOException {
        pool.shutdown();
        System.out.println("pool shutdown");
//        synchronized (USERS) {
//            for(User user : USERS) {
//                if(!user.getUserSocket().isClosed()) {
//                    user.getUserSocket().close();
//                }
//            }
//        }

        serverSocket.close();

    }*/


}
