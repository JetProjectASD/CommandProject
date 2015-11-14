package com.jet.edu.project03.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketStreamListener implements Runnable {

    private Logger logger = Logger.getLogger(SocketStreamListener.class.getName());
    private final List<User> users;
    private Long pseudoUserId = 0L;

    public SocketStreamListener(List<User> users) {
        this.users = users;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().interrupted()) {
            synchronized (users) {
                listenUserSockets();
            }
            try {
                Thread.currentThread().sleep(50);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "Error when thread sleep", e);
            }
        }
    }

    private void listenUserSockets() {
        try {
            for (User user : users) {
                BufferedReader userInputStream = user.getUserInputStream();
                if (checkStreamAndSocketAvailable(user, userInputStream)) {
                    ServerApp.pool.submit(new ClientWorker(user, users, ++pseudoUserId));
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Can`t get input stream from user");
        }
    }

    private boolean checkStreamAndSocketAvailable(User user, BufferedReader userInputStream) throws IOException {
        return userInputStream.ready() && !user.getUserSocket().isClosed();
    }
}
