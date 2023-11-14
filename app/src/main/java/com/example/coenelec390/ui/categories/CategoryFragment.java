package com.example.coenelec390.ui.categories;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.db_manager.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CategoryFragment extends Fragment implements  CategoryAdapter.OnItemClickListener{



    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private DatabaseManager databaseManager;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CategoryFragment newInstance(int columnCount) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        // Initialize the databaseManager object
        databaseManager = new DatabaseManager();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(categoryAdapter);

        // Fetch the main categories from Firebase
        databaseManager.fetchMainCategories(new DatabaseManager.OnMainCategoriesLoadedListener() {
            @Override
            public void onMainCategoriesLoaded(List<String> mainCategories) {
                // Update the adapter with the retrieved main categories
                categoryAdapter.setMainCategories(mainCategories);
            }

            @Override
            public void onMainCategoriesError(String errorMessage) {
                // Handle the error
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Utils.print(errorMessage);
            }
        });

        return view;
    }

    @Override
    public void onItemClick(String category) {
        Toast.makeText(getContext(), "Clicked category: " + category, Toast.LENGTH_SHORT).show();
        fetchSubCategories(category);

    }
    private void fetchSubCategories(String category) {
        // Fetch the subcategories for the selected category from Firebase
        databaseManager.fetchSubCategories(category, new DatabaseManager.OnSubCategoriesLoadedListener() {
            @Override
            public void onSubCategoriesLoaded(List<String> subCategories) {
                // Replace the current fragment with the SubCategoryFragment
                SubCategoryFragment subCategoryFragment = SubCategoryFragment.newInstance(subCategories);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.subCategoryFragment, subCategoryFragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onSubCategoriesError(String errorMessage) {
                // Handle possible errors.
                Toast.makeText(getContext(), "Error fetching subcategories: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}