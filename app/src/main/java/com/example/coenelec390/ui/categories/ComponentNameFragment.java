package com.example.coenelec390.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.ComponentDetailActivity;
import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.model.Component;

import java.util.ArrayList;
import java.util.List;

public class ComponentNameFragment extends Fragment implements ComponetNameAdapter.OnItemClickListener {
    private List<String> subCategories;
    private List<Component> components;
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

            // Set the dynamic title
                if (getActivity() != null) {
                    ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                    if (actionBar != null) {
                        String path = categoryName + "/"  + subCategoryName;
                        actionBar.setTitle(path);  // Replace with your dynamic title
                    }
                }
        viewModel.getComponents().observe(getViewLifecycleOwner(), new Observer<List<Component>>() {
            @Override
            public void onChanged(List<Component> _components) {
                components = _components;
            }
        });

        viewModel.getComponentNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> names) {
                componentNameAdapter.setComponentNames(names);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.list);
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
    public void onItemClick(Integer position) {
//        Toast.makeText(getContext(), "Clicked categorySUB: " + componentName, Toast.LENGTH_SHORT).show();
//        fetchComponents(categoryName, subCategory);
         Component component = components.get(position);
         addComponentDetailFragment(component);


    }
    public void addComponentDetailFragment(Component component) {

        //Bundle bundle = new Bundle();
        //bundle.putSerializable("component", component);
        //NavController navController = NavHostFragment.findNavController(this);
        //navController.navigate(R.id.action_componetNameFragment_to_componentDetailFragment, bundle);
        Intent intent = ComponentDetailActivity.newIntent(requireContext(), component);
        startActivity(intent);
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
