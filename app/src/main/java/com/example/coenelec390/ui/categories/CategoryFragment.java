package com.example.coenelec390.ui.categories;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
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
    private CategoryViewModel viewModel;
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
        //initialize the viewmodel,
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
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

        // Observe changes in categories from the ViewModel and update ui
        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            // UPDATE UI with new list of categories
            categoryAdapter.setMainCategories(categories);
        });
        return view;
    }

    @Override
    public void onItemClick(String category) {
        Toast.makeText(getContext(), "Clicked categoryMAIN: " + category, Toast.LENGTH_SHORT).show();
        categoryAdapter.clearData();

        // set the view Model
        viewModel.fetchSubCategories(category);
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", category);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_navigation_categories_to_subCategoryFragment, bundle);

//        fetchSubCategories(category);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        categoryAdapter.clearData(); // Clear the data when the view is destroyed
    }


}