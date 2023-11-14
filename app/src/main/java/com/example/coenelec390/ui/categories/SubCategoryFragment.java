package com.example.coenelec390.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private SubCategoryAdapter subCategoryAdapter;
    private List<String> subCategories;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public static SubCategoryFragment newInstance(List<String> subCategories) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("subCategories", new ArrayList<>(subCategories));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subCategories = getArguments().getStringArrayList("subCategories");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcategory_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subCategoryAdapter = new SubCategoryAdapter(subCategories, getContext());
        recyclerView.setAdapter(subCategoryAdapter);

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear the data in the adapter
        if (subCategoryAdapter != null) {
            subCategoryAdapter.clearData();
        }
        // Set the RecyclerView and Adapter to null to release resources
        recyclerView = null;
        subCategoryAdapter = null;
    }

}
