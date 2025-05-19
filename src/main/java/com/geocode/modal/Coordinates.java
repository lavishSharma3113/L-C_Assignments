package com.geocode.modal;

public class Coordinates {
    private final double latitude;
    private final double longitude;
    private final String name;
    private final String country;

    public Coordinates(double latitude, double longitude) {
        this(latitude, longitude, null, null);
    }

    public Coordinates(double latitude, double longitude, String name, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        String location = "";
        if (name != null && country != null) {
            location = String.format("Location: %s, %s\n", name, country);
        }
        return String.format("%sLatitude: %.4f\nLongitude: %.4f", location, latitude, longitude);
    }
}
