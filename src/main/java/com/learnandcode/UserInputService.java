package com.learnandcode;

import java.util.Scanner;

public class UserInputService {

    private final Scanner scanner;

    public UserInputService() {
        this.scanner = new Scanner(System.in);
    }

    public String getBlogName() {
        System.out.println("Enter the Tumblr Blog Name:");
        return scanner.nextLine().trim();
    }

    public int[] getRange() {
        System.out.println("Enter the Range (start-end):");
        String range = scanner.nextLine().trim();

        String[] rangeParts = range.split("-");

        try {
            int start = Integer.parseInt(rangeParts[0].trim());
            int end = Integer.parseInt(rangeParts[1].trim());
            return new int[]{start, end};
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid input format. Please enter in 'start-end' format (e.g., 10-20).");
            return getRange();
        }
    }
}


