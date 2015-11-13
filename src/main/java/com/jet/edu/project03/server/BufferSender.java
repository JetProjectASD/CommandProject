package com.jet.edu.project03.server;

import java.util.List;

public class BufferSender implements Runnable {
    private final List<String> messageBuffer;
    private Printer historyFileLogger;
    private final static String FILE_NAME = "chatHistory.txt";
    private final static String ENCODING = "UTF-8";

    public BufferSender(List<String> messageBuffer) {
        this.messageBuffer = messageBuffer;
        historyFileLogger = new Printer(FILE_NAME, ENCODING);
    }

    @Override
    public void run() {
        if (messageBuffer.size() > 9) {
            synchronized (messageBuffer) {
                sendBuffer();
            }
        }
    }

    private synchronized void sendBuffer() {
        StringBuilder builder = new StringBuilder();
        for(String string : messageBuffer) {
            builder.append(string).append(System.lineSeparator());
        }
        messageBuffer.clear();
        historyFileLogger.print(builder.toString());
    }
}
