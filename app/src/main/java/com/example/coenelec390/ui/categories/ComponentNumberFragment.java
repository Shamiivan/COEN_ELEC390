package com.example.coenelec390.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.model.Category;

import java.util.ArrayList;
import java.util.List;

public class ComponentNumberFragment extends Fragment implements ComponentNumberAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ComponentNumberAdapter componentNumberAdapter;
    private List<String> componentNames;
    private String categoryName;
    private String subCategoryName;
    private CategoryAdapter.OnItemClickListener listener;
    private CategoryViewModel viewModel;
    public ComponentNumberFragment() {
        // Required empty public constructor
    }

    public static ComponentNumberFragment newInstance(String categoryName) {
        ComponentNumberFragment fragment = new ComponentNumberFragment();
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
            subCategoryName = getArguments().getString("subCategoryName");
        }
        //get the view model to handle the data
        Utils.print("Calling from " + categoryName);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        viewModel.fetchComponents(categoryName, subCategoryName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcategory_list, container, false);

        recyclerView = view.findViewById(R.id.list);
        componentNumberAdapter = new ComponentNumberAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(componentNumberAdapter);
        viewModel.getComponentNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> _componentNames) {
                componentNumberAdapter.setComponentNames(_componentNames);
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
