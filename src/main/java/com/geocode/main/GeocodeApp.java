package com.geocode.main;


import com.geocode.controller.GeocodeController;
import com.geocode.services.GeocodingService;
import com.geocode.services.GeocodingServiceHandler;
import com.geocode.ui.ConsoleUserInterface;
import com.geocode.ui.UserInterface;

public class GeocodeApp {

    public static void main(String[] args) {
        GeocodingService geocodingService = new GeocodingServiceHandler();
        UserInterface userInterface = new ConsoleUserInterface();

        GeocodeController controller = new GeocodeController(geocodingService, userInterface);
        controller.start();
    }

}
