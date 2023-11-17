package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.coenelec390.R;
import com.example.coenelec390.databinding.ItemDetailsBinding;
public class ItemDetails extends Fragment {
    private ItemDetailsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ItemDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner cat = root.findViewById(R.id.category);
        cat.setEnabled(false);

        Spinner subCat = root.findViewById(R.id.subcategory);
        subCat.setEnabled(false);

        Button update = binding.updateItem;
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Edit button pressed", Toast.LENGTH_SHORT).show();

                EditText partName = root.findViewById(R.id.etID);
                partName.setEnabled(true);

                EditText location = root.findViewById(R.id.etLocation);
                location.setEnabled(true);

                EditText quantity = root.findViewById(R.id.etStock);
                quantity.setEnabled(true);

                EditText unitPrice = root.findViewById(R.id.etUnitPrice);
                unitPrice.setEnabled(true);

                cat.setEnabled(true);
                subCat.setEnabled(true);

                Button addTxtField = root.findViewById(R.id.btnAddDesc);
                addTxtField.setVisibility(View.VISIBLE);

                Button saveEdits = root.findViewById(R.id.saveEdits);
                saveEdits.setVisibility(View.VISIBLE);

                update.setVisibility(View.GONE);
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
