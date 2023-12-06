package model;

import androidx.annotation.NonNull;

public class Project {
    private String title;
    private String description;
    private int total;

    public Project(String title, String description, int total) {
        this.title = title;
        this.description = description;
        this.total = total;
    }

    public Project() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", total=" + total +
                '}';
    }
}
