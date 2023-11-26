package com.example.coenelec390.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.model.Category;
import com.example.coenelec390.model.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.ui.item.ComponentDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment implements SubCategoryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private SubCategoryAdapter subCategoryAdapter;
    private List<String> subCategories;
    private String categoryName;
    private CategoryAdapter.OnItemClickListener listener;
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
        Utils.print("Calling from " + categoryName);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        viewModel.fetchSubCategories(categoryName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcategory_list, container, false);

        recyclerView = view.findViewById(R.id.list);
        subCategoryAdapter = new SubCategoryAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subCategoryAdapter);
        viewModel.getSubCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> _subCategories) {
                subCategoryAdapter.setSubCategories(_subCategories);
                for (Category category : _subCategories) {
                    category.display();
                }
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
    public void onItemClick(String subCategory) {
        Toast.makeText(getContext(), "Clicked categorySUB: " + subCategory, Toast.LENGTH_SHORT).show();
        Utils.print(subCategory + " " + categoryName);
//        fetchComponents(categoryName, subCategory);



    }

//    public void fetchComponents(String mainCategory, String subCategory){
//        databaseManager.fetchComponents(mainCategory, subCategory, new DatabaseManager.OnComponentLoadedListener() {
//            @Override
//            public void onComponentLoaded(List<Component> components) {
//                for (Component component: components) {
//                    component.setMainCategory(mainCategory);
//                    component.setSubCategory(subCategory);
//                    component.display();
//                }
//                // Start the ComponentDetailFragment
//                ComponentDetailFragment componentDetailFragment = ComponentDetailFragment.newInstance(components);
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.nav_host_fragment_activity_main, componentDetailFragment);
//                transaction.addToBackStack(null); // Optional: Add to back stack for fragment navigation
//                transaction.commit();
//            }
//
//            @Override
//            public void onComponentError(String errorMessage) {
//                Toast.makeText(getContext(), "Error fetching subcategories: " + errorMessage, Toast.LENGTH_SHORT).show();
//                Utils.print(errorMessage);
//            }
//        });
//    }
}
