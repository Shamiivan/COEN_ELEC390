package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.coenelec390.R;
import com.example.coenelec390.databinding.FragmentItemBinding;

import java.util.ArrayList;

public class ItemFragment extends Fragment {

    private FragmentItemBinding binding;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.list);

        arrayList = new ArrayList<>();//Dummy array
        arrayList.add("CRCW06031K00FKTA");
        arrayList.add("CRCW060349R9FKTA");
        arrayList.add("ERA-6AED333V");
        arrayList.add("06035C104K4Z4A");
        arrayList.add("C0603X220J5GACAUTO");
        arrayList.add("IHLP4040EDER220M5A");

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        Button viewCat = binding.viewCat;
        viewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

        Button viewList = binding.viewList;
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
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