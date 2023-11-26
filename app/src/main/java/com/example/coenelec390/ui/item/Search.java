package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.coenelec390.R;
import com.example.coenelec390.databinding.PageSearchBinding;
import com.example.coenelec390.db_manager.Component;

import java.util.ArrayList;

public class Search extends Fragment {
    SearchView searchView;
    ListView listView;

    ArrayList<Component> arrayList;
    ArrayAdapter<Component> adapter;
    private PageSearchBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = PageSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        searchView = root.findViewById(R.id.searchBar);
        listView = root.findViewById(R.id.searchlist);

        arrayList = new ArrayList<>();//Dummy array TODO: Remove after
        Component com1 = new Component("tag1", "Active", "MOSFET", "Part Number1", 0.5, 5, "Location1", null);
        Component com2 = new Component("tag2", "Passive", "Resistor", "Part Number2", 0.1, 10, "Location2", null);
        arrayList.add(com1);
        arrayList.add(com2);

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

        Button viewActive = binding.viewActive;
        ArrayList<Component> actives = new ArrayList<>();
        filterCategory(viewActive, actives, "Active");

        Button viewPassive = binding.viewPassive;
        ArrayList<Component> passives = new ArrayList<>();
        filterCategory(viewPassive, passives, "Passive");

        Button viewList = binding.viewList;
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);
            }
        });

        return root;
    }

    private void filterCategory(Button viewCat, ArrayList<Component> catList, String category) {
        for (Component com : arrayList) {
            if (com.getMainCategory() == category) {
                catList.add(com);
            }
        }

        viewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, catList);
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
