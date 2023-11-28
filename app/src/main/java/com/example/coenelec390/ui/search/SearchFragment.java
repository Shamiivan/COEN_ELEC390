package com.example.coenelec390.ui.search;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.databinding.PageSearchBinding;
import com.example.coenelec390.model.Component;
import com.example.coenelec390.ui.categories.ComponetNameAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment  implements SearchAdapter.OnItemClickListener{
    SearchView searchView;

    List<Component> arrayList;
    List<String> componentNames;
//    ArrayAdapter<Component> adapter;
    private PageSearchBinding binding;
    SearchAdapter adapter;

    private SearchViewModel viewModel;

    public SearchFragment(){

    }

    public static SearchFragment newInstance(){
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            //TODO
        }


        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        // load the components to be used in
        viewModel.loadComponents();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = PageSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.searchBar);
        RecyclerView recyclerView = (RecyclerView) binding.searchlist;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SearchAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);


        // Observe the components LiveData from the ViewModel
        viewModel.getComponents().observe(getViewLifecycleOwner(), new Observer<List<Component>>() {
            @Override
            public void onChanged(List<Component> componentsList) {
                // Update the ListView with the components data
                // For example, display the components in a ListView

            }
        });

        viewModel.getComponentNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> names) {
                componentNames = names;
                adapter.setComponentNames(names);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return root;
    }
    void filter(String text){
    Utils.print(text);
    adapter.setComponentNames(componentNames);
        if (componentNames == null) {
            return;
        }
        List<String> temp = new ArrayList();
        for(String d: componentNames){
            if(d.toLowerCase().contains(text.toLowerCase())){
                temp.add(d);

            }
        }
        // Update RecyclerView
        adapter.updateList(temp);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(String componentName) {
        Utils.display(getContext(), componentName);
    }
}
