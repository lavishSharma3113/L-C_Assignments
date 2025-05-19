package com.geocode.ui;

public interface UserInterface {
    String getInput(String prompt);
    void displayOutput(String output);
    void displayError(String errorMessage);
}
