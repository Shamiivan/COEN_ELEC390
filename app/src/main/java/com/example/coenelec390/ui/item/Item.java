package com.example.coenelec390.ui.item;

public class Item {
    private String id;
    private String location;
    private String description;
    private int stock;

    public Item(){

    }

    public Item(String id, String location, String description, int stock){
        this.id = id;
        this.location = location;
        this.description = description;
        this.stock = stock;
    }

    public String getId(){
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }
}
