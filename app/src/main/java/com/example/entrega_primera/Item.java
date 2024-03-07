package com.example.entrega_primera;

public class Item {
    private int id;
    private String brand;
    private String model;
    private float price;

    public Item(int id, String brand, String model, float price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
    }

    public int getId() { return this.id; }
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
