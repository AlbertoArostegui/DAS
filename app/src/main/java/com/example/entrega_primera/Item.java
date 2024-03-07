package com.example.entrega_primera;

public class Item {
    private String brand;
    private String model;
    private float price;

    public Item(String brand, String model, float price) {
        this.brand = brand;
        this.model = model;
        this.price = price;
    }

    public String getBrand() {
        return this.brand;
    }
    public String getModel() {
        return this.model;
    }
    public float getPrice() {
        return this.price;
    }
}
