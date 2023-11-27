package com.example.coenelec390.ui.search;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coenelec390.model.Component;

import com.example.coenelec390.db_manager.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<Component> component;
    private MutableLiveData<List<Component>> components;
    private MutableLiveData <List<Component>> componentsFound;
    private MutableLiveData <List<String>> componentNames;
    private MutableLiveData<String> componentsError;
    private DatabaseManager databaseManager;

    public SearchViewModel() {
        component = new MutableLiveData<>();
        components = new MutableLiveData<>();
        componentsFound = new MutableLiveData<>();
        componentNames = new MutableLiveData<>();
        databaseManager = new DatabaseManager();
    }

    public LiveData<Component> getComponent() {
        return component;
    }

    public void searchComponent(String searchQuery) {
        databaseManager.DatabaseSearch(searchQuery, new DatabaseManager.ComponentSearchCallback() {
            @Override
            public void onComponentsFound(List<Component> components) {
                if (!components.isEmpty()) {
                    component.setValue(components.get(0));
                } else {
                    // handle the case when no matching components are found
                }
            }

            @Override
            public void onError(String error) {
                // handle the error
            }
        });
    }



    // search all the components
    public void loadComponents(){
        List<String> names = new ArrayList<>();
        databaseManager.fetchAllComponents(new DatabaseManager.OnAllComponentsLoadedListener() {
            @Override
            public void onComponentsLoaded(List<Component> _components) {
                components.setValue(_components);
                for (Component component:_components
                     ) {
                    names.add(component.getPartNumber());
                }
                componentNames.setValue(names);
            }

            @Override
            public void onError(String errorMessage) {
                componentsError.setValue(errorMessage);
            }
        });
    }

    public MutableLiveData<List<Component>> getComponents() {
        return components;
    }

    public MutableLiveData<List<String>> getComponentNames() {
        return componentNames;
    }

    public void searchComponents(String query) {
        List<Component> allComponents = components.getValue();
        if (allComponents != null) {
            List<Component> searchResults = new ArrayList<>();
            for (Component component : allComponents) {
                if (component.getMainCategory().contains(query) ||
                        component.getSubCategory().contains(query) ||
                        component.getPartNumber().contains(query)) {
                    searchResults.add(component);
                }
            }
            componentsFound.setValue(searchResults);
        }
    }

    public MutableLiveData<List<Component>> getComponentsFound() {
        return componentsFound;
    }
}

