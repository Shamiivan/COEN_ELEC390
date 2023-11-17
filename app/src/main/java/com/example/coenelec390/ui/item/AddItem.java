package com.example.coenelec390.ui.item;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.coenelec390.R;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coenelec390.databinding.AddItemBinding;


public class AddItem extends androidx.fragment.app.DialogFragment {
    private Spinner cat;
    private Spinner subCat;
    ArrayAdapter<CharSequence> adapter;
    private Button save;
    private AddItemBinding binding;

    public AddItem(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootview = inflater.inflate(R.layout.add_item, container, false);

        cat = rootview.findViewById(R.id.category);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cat.setAdapter(adapter);

        subCat = rootview.findViewById(R.id.subcategory);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.Subcategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        subCat.setAdapter(adapter);

        Button addTxt = rootview.findViewById(R.id.btnAddDesc);
        addTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = rootview.findViewById(R.id.extraDesc);
                EditText desc = new EditText(getContext());
                layout.addView(desc);
            }
        });
        /*save = binding.btnAddProduct;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Component Added!", Toast.LENGTH_SHORT).show();
            }
        });*/

        return rootview;
    }
}