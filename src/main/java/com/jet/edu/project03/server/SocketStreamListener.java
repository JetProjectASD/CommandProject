package com.jet.edu.project03.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class SocketStreamListener implements Runnable {

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
                e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private boolean checkStreamAndSocketAvailable(User user, BufferedReader userInputStream) throws IOException {
        return userInputStream.ready() && !user.getUserSocket().isClosed();
    }
}
