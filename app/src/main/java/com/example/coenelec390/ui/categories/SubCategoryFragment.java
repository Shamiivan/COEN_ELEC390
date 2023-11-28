package com.example.coenelec390.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.model.Category;
import com.example.coenelec390.model.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.model.SubCategory;
import com.example.coenelec390.ui.item.ComponentDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment implements SubCategoryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private SubCategoryAdapter subCategoryAdapter;
    private List<String> subCategories;
    private String categoryName;
    private CategoryViewModel viewModel;
    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(String categoryName) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString("categoryName", categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString("categoryName");
        }
        //get the view model to handle the data
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        viewModel.fetchSubCategories(categoryName);
        // Handle the back button event
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event

                Utils.print("Back button pressed");
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcategory_list, container, false);

        recyclerView = view.findViewById(R.id.list);
        subCategoryAdapter = new SubCategoryAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subCategoryAdapter);
        viewModel.getSubCategories().observe(getViewLifecycleOwner(), new Observer<List<SubCategory>>() {
            @Override
            public void onChanged(List<SubCategory> _subCategories) {
                subCategoryAdapter.setSubCategories(_subCategories);
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        // Clear the data in the adapter
//        if (subCategoryAdapter != null) {
//            subCategoryAdapter.clearData();
//        }
//        // Set the RecyclerView and Adapter to null to release resources
//        recyclerView = null;
//        subCategoryAdapter = null;
    }

    @Override
    public void onItemClick(SubCategory subCategory) {
        Toast.makeText(getContext(), "Clicked categorySUB: " + subCategory.getName(), Toast.LENGTH_SHORT).show();
        Utils.print(subCategory + " " + categoryName);
        String category = subCategory.getParentName();
        String subCategoryName = subCategory.getName();
        viewModel.fetchComponents(category,subCategoryName);

        Bundle bundle = new Bundle();
        bundle.putString("subCategory", subCategory.getName());
        bundle.putString("category", subCategory.getParentName());

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_subCategoryFragment_to_componetNameFragment, bundle);
//        fetchComponents(categoryName, subCategory);
    }

}
