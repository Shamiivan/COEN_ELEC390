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
    private MutableLiveData<List<Category>> subCategories;
    private MutableLiveData<String> subCategoriesError;
    private final DatabaseManager databaseManager;

    public CategoryViewModel() {
        categories = new MutableLiveData<>();
        categoriesError = new MutableLiveData<>();
        subCategories = new MutableLiveData<>();
        subCategoriesError = new MutableLiveData<>();
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


    public void fetchSubCategories(String category){
        databaseManager.fetchSubCategories(category, new DatabaseManager.OnSubCategoriesLoadedListener() {
            @Override
            public void onSubCategoriesLoaded(List<Category> _subCategories) {
                for (Category category : _subCategories) {
                    category.display();
                }
                subCategories.setValue(_subCategories);
            }

            @Override
            public void onSubCategoriesError(String errorMessage) {
            subCategoriesError.setValue(errorMessage);
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

    public MutableLiveData<List<Category>> getSubCategories() {
        return subCategories;
    }

    public MutableLiveData<String> getSubCategoriesError() {
        return subCategoriesError;
    }
}

