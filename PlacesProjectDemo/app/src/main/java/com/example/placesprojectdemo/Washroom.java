package com.example.placesprojectdemo;

import java.io.Serializable;

public class Washroom implements Serializable {
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    // Add other fields as needed

    public Washroom() {
        // Default constructor required for Firebase
    }

    public Washroom(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                // Add other fields as needed
                '}';
    }
}
