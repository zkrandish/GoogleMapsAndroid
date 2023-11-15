package com.example.placesprojectdemo;

import java.io.Serializable;

public class Washroom implements Serializable {
    private String name;
    private String details;
    private double latitude;
    private double longitude;

    // Add other fields as needed

    public Washroom() {
        // Default constructor required for Firebase
    }

    public Washroom(String name, String details, double latitude, double longitude) {
        this.name = name;
        this.details = details;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Add getters and setters for other fields

    @Override
    public String toString() {
        return "Washroom{" +
                "name='" + name + '\'' +
                ", address='" + details + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                // Add other fields as needed
                '}';
    }
}
