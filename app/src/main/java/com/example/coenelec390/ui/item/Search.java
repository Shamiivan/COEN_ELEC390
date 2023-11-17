package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.coenelec390.R;
import com.example.coenelec390.databinding.PageSearchBinding;

import java.util.ArrayList;

public class Search extends Fragment {
    SearchView searchView;
    ListView listView;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    private PageSearchBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = PageSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.searchBar);
        listView = root.findViewById(R.id.searchlist);

        arrayList = new ArrayList<>();//Dummy array
        arrayList.add("CRCW06031K00FKTA");
        arrayList.add("CRCW060349R9FKTA");
        arrayList.add("ERA-6AED333V");
        arrayList.add("06035C104K4Z4A");
        arrayList.add("C0603X220J5GACAUTO");
        arrayList.add("IHLP4040EDER220M5A");

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
