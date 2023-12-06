package model;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class Schedule {
    private int id;
    private String description;
    private  int txtColor;

    public Schedule(int id, String description, int txtColor) {
        this.id = id;
        this.description = description;
        this.txtColor = txtColor;
    }

    public Schedule(int id, String description) {
        this.id = id;
        this.description = description;
        this.txtColor= Color.GREEN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTxtColor() {
        return txtColor;
    }

    public void setTxtColor(int txtColor) {
        this.txtColor = txtColor;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
