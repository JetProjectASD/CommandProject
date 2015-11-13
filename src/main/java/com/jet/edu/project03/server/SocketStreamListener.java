package com.jet.edu.project03.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class SocketStreamListener implements Runnable {

    private final List<User> users;
    private ExecutorService pool;
    private Long pseudoUserId = 0L;

    public SocketStreamListener(List<User> users, ExecutorService pool) {
        this.users = users;
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (users) {
                try {
                    for (User user : users) {
                        InputStream userInputStream = user.getUserInputStream();
                        if (userInputStream.available() != 0 && !user.getUserSocket().isClosed()) {
                            pool.submit(new ClientWorker(user, users, pool, ++pseudoUserId));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
