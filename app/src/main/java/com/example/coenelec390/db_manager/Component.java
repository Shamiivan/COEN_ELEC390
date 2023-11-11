package com.example.coenelec390.db_manager;
import java.util.Map;

public class Component {
    private Map<String, String> characteristics;

    private String location;
    private String MainCategory;
    private String SubCategory;
    private String PartNumber;
    private double unitPrice;
    private int quantity;
    private double totalPrice;

    public Component(Map<String, String> characteristics, String location, int quantity) {
        this.characteristics = characteristics;
        this.location = location;
        this.quantity = quantity;
    }
    public Component() {
        // Default constructor required for Firebase
    }
    public Component(String mainCategory, String subCategory, String partNumber, double unitPrice, int quantity,String location, Map<String, String> characteristics) {
        this.characteristics = characteristics;
        this.location = location;
        this.MainCategory = mainCategory;
        this.SubCategory = subCategory;
        this.PartNumber = partNumber;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = quantity * unitPrice;
    }

    public Map<String, String> getCharacteristics() {
        return characteristics;
    }

    public String getLocation() {
        return location;
    }

    public String getMainCategory() {
        return MainCategory;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public String getPartNumber() {
        return PartNumber;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


}