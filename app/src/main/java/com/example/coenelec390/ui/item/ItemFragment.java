package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.coenelec390.databinding.FragmentItemBinding;

public class ItemFragment extends Fragment {

    private FragmentItemBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button add = binding.additem;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddItem a = new AddItem();
                a.show(getActivity().getSupportFragmentManager(), "Add Fragment");
                //AddItem m =  AddItem.newInstance("S");

            }
        });

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
                ListItems v = new ListItems();
                v.show(getActivity().getSupportFragmentManager(), "View List Fragment");
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