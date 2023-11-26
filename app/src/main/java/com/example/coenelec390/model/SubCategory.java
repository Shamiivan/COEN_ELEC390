package com.example.coenelec390.model;

import com.example.coenelec390.Utils;

public class SubCategory {
    private String parentName;
    private String name;


    private  long childCount;
    // empty constructor is left for
    public SubCategory() {
    }

    public SubCategory(String name){
        this.name = name;
    }

    public SubCategory(String name, String parentName,long childCount){
        this.name = name;
        this.parentName = parentName;
        this.childCount = childCount;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
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

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void display(){
        Utils.print(name);
        Utils.print(String.valueOf(childCount));
    }
}
