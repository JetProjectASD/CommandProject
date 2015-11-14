package com.jet.edu.project03.server;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class PrinterTest {

    @Test
    public void shouldWriteInFileWhenPrintString() throws IOException {
        ChatHistoryPrinter printer = new ChatHistoryPrinter("test.txt", "utf-8");
        String testString = "";
        printer.print("test string" + System.lineSeparator());
        testString = testString.concat("test string" + System.lineSeparator());
        printer.print("blabla" + System.lineSeparator());
        testString = testString.concat("blabla" + System.lineSeparator());

        String realFile = FileUtils.readFileToString(new File("test.txt"));
        assertEquals(realFile, testString);
        FileUtils.forceDelete(new File("test.txt"));
    }
}
