package com.example.coenelec390.ui.categories;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.model.Category;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    // MutableLiveData to hold a string data

    private MutableLiveData<List<Category>> categories;
    private MutableLiveData<String> categoriesError;
    private final DatabaseManager databaseManager;

    public CategoryViewModel() {
        categories = new MutableLiveData<>();
        categoriesError = new MutableLiveData<>();
        databaseManager = new DatabaseManager();
        fetchMainCategories();
    }

    public void fetchMainCategories(){

        databaseManager.fetchMainCategories(new DatabaseManager.OnMainCategoriesLoadedListener() {
            @Override
            public void onMainCategoriesLoaded(List<Category> mainCategories) {
                categories.setValue(mainCategories);
            }

            @Override
            public void onMainCategoriesError(String errorMessage) {
                categoriesError.setValue(errorMessage);
            }
        });
    }
    // returns a read only version
    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<String> getCategoriesError(){
        return categoriesError;
    }
}

