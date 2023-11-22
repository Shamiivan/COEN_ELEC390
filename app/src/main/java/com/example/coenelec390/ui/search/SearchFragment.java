package com.example.coenelec390.ui.search;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.ui.item.ComponentAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private SearchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private ComponentAdapter componentAdapter;
    private SearchView searchView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_view, container, false);

        // Initialize your views
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        componentAdapter = new ComponentAdapter(new ArrayList<>());
        recyclerView.setAdapter(componentAdapter);
        //         Initialize your SearchView
         searchView = root.findViewById(R.id.searchView);

//         Set up the query listener for your SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                searchViewModel.searchComponents(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query change
                // You can implement real-time filtering here if you want
                return false;
            }
        });


        // Initialize your ViewModel
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Observe the LiveData object in the ViewModel
        searchViewModel.getSearchResults().observe(getViewLifecycleOwner(), new Observer<List<Component>>() {
            @Override
            public void onChanged(List<Component> components) {
                // Update the RecyclerView
                componentAdapter.setComponents(components);
            }
        });


        return root;
    }


}
