package com.jet.edu.project03.loadingtest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(1000);

    public static void main(String[] args) throws InterruptedException {
        // args[0] - number clients
        // args[1] - host
        // args[2] - port
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            executor.submit(new MockClient(args[1], Integer.parseInt(args[2]), "usname" + i, "myRoom", "test message "));
            Thread.sleep(150);
        }

    }
}
