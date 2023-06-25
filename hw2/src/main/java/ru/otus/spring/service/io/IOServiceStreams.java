package ru.otus.spring.service.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements IOService {

    private final PrintStream printStream;

    private final Scanner scanner;

    public IOServiceStreams(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        outputString(prompt);
        return readString();
    }

    @Override
    public void outputString(String string) {
        printStream.println(string);
    }
}