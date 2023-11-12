package model;

import java.io.Serializable;
import java.util.Arrays;

public class Client implements Serializable {

    private int clNumber;
    private int transType;
    private int nbKlm;

    public Client(int clNumber, int transType, int nbKlm) {
        this.clNumber = clNumber;
        this.transType = transType;
        this.nbKlm = nbKlm;
    }

    public int getClNumber() {
        return clNumber;
    }

    public void setClNumber(int clNumber) {
        this.clNumber = clNumber;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public int getNbKlm() {
        return nbKlm;
    }

    public void setNbKlm(int nbKlm) {
        this.nbKlm = nbKlm;
    }

    @Override
    public String toString() {
       String tarnsTypeStirng= "";
       if(transType==1){
           tarnsTypeStirng="Bus";
       } else if (transType==2) {
           tarnsTypeStirng="Metro";
       }
       else if (transType==3) {
           tarnsTypeStirng="Private";
       } else if (transType==4) {
           tarnsTypeStirng="Taxi";
       }

        return "Client{" +
                "clNumber=" + clNumber +
                ", transType=" + tarnsTypeStirng+
                ", nbKlm=" + nbKlm +
                '}';
    }
}
