package com.example.coenelec390.ui.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.databinding.FragmentComponentDetailBinding;
import com.example.coenelec390.db_manager.Component;

import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ComponentViewHolder> {
    private List<Component> components;

    public ComponentAdapter(List<Component> components) {
        this.components = components;
    }

    @NonNull
    @Override
    public ComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentComponentDetailBinding binding = FragmentComponentDetailBinding.inflate(inflater, parent, false);
        return new ComponentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentViewHolder holder, int position) {
        Component component = components.get(position);
        holder.bind(component);
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public void setComponents(List<Component> components) {
    }

    public class ComponentViewHolder extends RecyclerView.ViewHolder {
        private FragmentComponentDetailBinding binding;

        public ComponentViewHolder(FragmentComponentDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Component component) {
                // Bind your data to the views using the binding instance
                binding.textViewTag.setText("Tag: " + component.getTag());
                binding.textViewLocation.setText("Location: " + component.getLocation());
                binding.textViewMainCategory.setText("Main Category: " + component.getMainCategory());
                binding.textViewSubCategory.setText("Sub Category: " + component.getSubCategory());
                binding.textViewPartNumber.setText("Part Number: " + component.getPartNumber());
                binding.textViewUnitPrice.setText("Unit Price: " + component.getUnitPrice());
                binding.textViewQuantity.setText("Quantity: " + component.getQuantity());
                binding.textViewTotalPrice.setText("Total Price: " + component.getTotalPrice());
                binding.textViewCharacteristics.setText("Characteristics: " + component.getCharacteristics().toString());

        }
    }
}
