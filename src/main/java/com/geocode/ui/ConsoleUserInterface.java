package com.geocode.ui;

import java.util.Scanner;

public class ConsoleUserInterface implements UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    @Override
    public void displayOutput(String output) {
        System.out.println(output);
    }

    @Override
    public void displayError(String errorMessage) {
        System.err.println("ERROR: " + errorMessage);
    }

    public void close() {
        scanner.close();
    }
}
