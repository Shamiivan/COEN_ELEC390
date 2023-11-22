package com.example.coenelec390.ui.search;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private final DatabaseManager databaseManager;
    private MutableLiveData<List<Component>> searchResults;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        databaseManager = new DatabaseManager();
        searchResults = new MutableLiveData<>();
    }

    public MutableLiveData<List<Component>> getSearchResults() {
        return searchResults;
    }

    public void searchComponents(String searchQuery) {
        databaseManager.DatabaseSearch(searchQuery, new DatabaseManager.ComponentSearchCallback() {
            @Override
            public void onComponentsFound(List<Component> components) {
                for (Component c:components
                     ) {
                    c.display();
                }
                searchResults.postValue(components);
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }
}
