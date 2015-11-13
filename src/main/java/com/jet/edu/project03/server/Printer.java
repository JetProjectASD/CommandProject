package com.jet.edu.project03.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Flush server buffer to file for history accumulate
 */
public class Printer {

    private String fileName;
    private String encoding;

    /**
     * Initialize path file and charset type
     *
     * @param fileName - path
     * @param encoding - charset
     */
    public Printer(String fileName, String encoding) {
        this.fileName = fileName;
        this.encoding = encoding;
    }

    /**
     * Print message in file
     *
     * @param message - message
     */
    public void print(String message) {
        try (OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(fileName, true), encoding)) {
            streamWriter.write(message);
            streamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
