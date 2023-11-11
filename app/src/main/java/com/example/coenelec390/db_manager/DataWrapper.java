package com.example.coenelec390.db_manager;

import java.util.List;

public class DataWrapper {
    private List<Component> components;
    private List<String> mainCategories;

    private DataWrapper(List<Component> components) {
        this.components = components;
    }

    private DataWrapper(List<String> mainCategories) {
        this.mainCategories = mainCategories;
    }

    public static DataWrapper withComponents(List<Component> components) {
        return new DataWrapper(components);
    }

    public static DataWrapper withMainCategories(List<String> mainCategories) {
        return new DataWrapper(mainCategories);
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<String> getMainCategories() {
        return mainCategories;
    }
}
