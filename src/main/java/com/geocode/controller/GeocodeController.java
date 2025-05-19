package com.geocode.controller;

import com.geocode.exceptions.ApiException;
import com.geocode.exceptions.GeocodingException;
import com.geocode.modal.Coordinates;
import com.geocode.services.GeocodingService;
import com.geocode.ui.ConsoleUserInterface;
import com.geocode.ui.UserInterface;

public class GeocodeController {
    private final GeocodingService geocodingService;
    private final UserInterface userInterface;

    public GeocodeController(GeocodingService geocodingService, UserInterface userInterface) {
        this.geocodingService = geocodingService;
        this.userInterface = userInterface;
    }

    public void start() {
        try {
            String placeName = userInterface.getInput("Enter a place name: ");

            if (placeName.isEmpty()) {
                userInterface.displayError("Place name cannot be empty.");
                return;
            }

            try {
                Coordinates coordinates = geocodingService.geocode(placeName);
                userInterface.displayOutput(coordinates.toString());
            } catch (GeocodingException | ApiException e) {
                userInterface.displayError(e.getMessage());
            }

        } finally {
            if (userInterface instanceof ConsoleUserInterface) {
                ((ConsoleUserInterface) userInterface).close();
            }
        }
    }
}

