package com.example.coenelec390.ui.categories;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.model.Category;
import com.example.coenelec390.model.Component;
import com.example.coenelec390.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    // MutableLiveData to hold a string data
    private MutableLiveData<List<Category>> categories;
    private MutableLiveData<String> categoriesError;
    private MutableLiveData<List<SubCategory>> subCategories;
    private MutableLiveData<String> subCategoriesError;
    private  MutableLiveData<List<String>> componentNames;
    private MutableLiveData<List<Component>>components;
    private final DatabaseManager databaseManager;

    public CategoryViewModel() {
        categories = new MutableLiveData<>();
        categoriesError = new MutableLiveData<>();
        subCategories = new MutableLiveData<>();
        subCategoriesError = new MutableLiveData<>();
        componentNames = new MutableLiveData<>();
        components = new MutableLiveData<>();
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
            public void onSubCategoriesLoaded(List<SubCategory> _subCategories) {
                for (SubCategory subCategory : _subCategories) {
                    subCategory.display();
                }
                subCategories.setValue(_subCategories);
            }

            @Override
            public void onSubCategoriesError(String errorMessage) {
            subCategoriesError.setValue(errorMessage);
            }
        });
    }

    public void fetchComponents(String mainCategory, String subCategory){
        List<String> _componentsName = new ArrayList<>();
        databaseManager.fetchComponents(mainCategory, subCategory, new DatabaseManager.OnComponentsLoadedListener() {
            @Override
            public void onComponentsLoaded(List<Component> _components) {
                for (Component component: _components) {
                   _componentsName.add(component.getPartNumber());
                }
                componentNames.setValue(_componentsName);
                components.setValue(_components);
            }

            @Override
            public void onComponentError(String errorMessage) {

            }
        });
    }
   public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<String> getCategoriesError(){
        return categoriesError;
    }

    public MutableLiveData<List<SubCategory>> getSubCategories() {
        return subCategories;
    }

    public MutableLiveData<String> getSubCategoriesError() {
        return subCategoriesError;
    }

    public MutableLiveData<List<String>> getComponentNames() {
        return componentNames;
    }

    public MutableLiveData<List<Component>> getComponents() {
        return components;
    }
}

