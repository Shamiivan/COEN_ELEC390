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

public class ComponentNameFragment extends Fragment implements ComponetNameAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private SubCategoryAdapter subCategoryAdapter;
    private List<String> subCategories;
    private String categoryName;
    private String subCategoryName;
    private CategoryViewModel viewModel;
    private ComponetNameAdapter componentNameAdapter;
    public ComponentNameFragment() {
        // Required empty public constructor
    }

    public static ComponentNameFragment newInstance(String categoryName, String subCategory) {
        ComponentNameFragment fragment = new ComponentNameFragment();
        Bundle args = new Bundle();
        args.putString("subCategory", subCategory);
        args.putString("category", categoryName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryName = getArguments().getString("category");
            subCategoryName = getArguments().getString("subCategory");
        }
        //get the view model to handle the data
        Utils.print("Calling from " + categoryName + " " + subCategoryName);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        viewModel.fetchComponents(categoryName, subCategoryName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_name_list, container, false);

        viewModel.getComponentNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> names) {
                componentNameAdapter.setComponentNames(names);
            }
        });
        recyclerView = view.findViewById(R.id.list);
        componentNameAdapter = new ComponetNameAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(componentNameAdapter);

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
    public void onItemClick(String componentName) {
        Toast.makeText(getContext(), "Clicked categorySUB: " + componentName, Toast.LENGTH_SHORT).show();
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
