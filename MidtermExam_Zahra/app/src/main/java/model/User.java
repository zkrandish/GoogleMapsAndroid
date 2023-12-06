package model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String takeBus;
    private String takeMetro;
    private String takeCar;
    private String takeBike;

    public User(String userName, String takeBus, String takeMetro, String takeCar, String takeBike) {
        this.userName = userName;
        this.takeBus = takeBus;
        this.takeMetro = takeMetro;
        this.takeCar = takeCar;
        this.takeBike = takeBike;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTakeBus() {
        return takeBus;
    }

    public void setTakeBus(String takeBus) {
        this.takeBus = takeBus;
    }

    public String getTakeMetro() {
        return takeMetro;
    }

    public void setTakeMetro(String takeMetro) {
        this.takeMetro = takeMetro;
    }

    public String getTakeCar() {
        return takeCar;
    }

    public void setTakeCar(String takeCar) {
        this.takeCar = takeCar;
    }

    public String getTakeBike() {
        return takeBike;
    }

    public void setTakeBike(String takeBike) {
        this.takeBike = takeBike;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", takeBus='" + takeBus + '\'' +
                ", takeMetro='" + takeMetro + '\'' +
                ", takeCar='" + takeCar + '\'' +
                ", takeBike='" + takeBike + '\'' +
                '}';
    }
}
