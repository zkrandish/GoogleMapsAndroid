package model;

import java.io.Serializable;

public class Order implements Serializable {
    private int clientNumber;
    private String pizzaType;
    private int nbSlices;

    public Order(int clientNumber, String pizzaType, int nbSlices) {
        this.clientNumber = clientNumber;
        this.pizzaType = pizzaType;
       this.nbSlices = nbSlices;
    }

    public Order(int clientNumber, String pizzaType) {
        this.clientNumber = clientNumber;
        this.pizzaType = pizzaType;
        this.nbSlices=1;
    }



    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }

    public int getNbSlices() {
        return nbSlices;
    }

    public void setNbSlices(int nbSlices) {
        this.nbSlices = nbSlices;
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + clientNumber +
                ", pizza='" + pizzaType + '\'' +
                ", nbSlices=" + nbSlices +
                "amount: "+getAmount(pizzaType,nbSlices)+
                '}';
    }

    public static float getAmount(String pizzaType,int nbSlices)  {
        try{
            return getPrice(pizzaType)*nbSlices;
        } catch (Exception e){
            return -1;
        }

    }
    private static float getPrice(String pizzaType) throws Exception {
        float price;
        if(pizzaType.equals("vegetarian")){
            price=2.5f;
        } else if (pizzaType.equals("Mexican")) {
            price=2.4f;

        } else if (pizzaType.equals("Cheese")) {
            price=2.0f;

        }
        else {
            throw new Exception("please select pizza");
        }
        return price;
    }

}
