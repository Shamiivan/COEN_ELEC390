package com.example.coenelec390.model;

public class Category {
    private String name;


    private  long childCount;
    // empty constructor is left for
    public Category() {
    }

    public  Category (String name){
        this.name = name;
    }

    public  Category (String name, long childCount){
        this.name = name;
        this.childCount = childCount;
    }

    public String getName() {
        return name;
    }

    public long getChildCount() {
        return childCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }
}
