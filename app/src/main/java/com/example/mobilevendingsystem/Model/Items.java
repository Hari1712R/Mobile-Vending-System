package com.example.mobilevendingsystem.Model;

import java.io.Serializable;

public class Items implements Serializable {
    private String itemName;
    private String quantity;
    private String price;

    public Items(String itemName, String quantity, String price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public double getDoublePrice() {return Double.valueOf(price);}

    public void setPrice(String price) {
        this.price = price;
    }
}
