package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
                AddItemActivity a = new AddItemActivity();
                a.show(getActivity().getSupportFragmentManager(), "Add Fragment");
            }
        });

        Button view = binding.listItem;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItemsActivity v = new ListItemsActivity();
                v.show(getActivity().getSupportFragmentManager(), "View Fragment");
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