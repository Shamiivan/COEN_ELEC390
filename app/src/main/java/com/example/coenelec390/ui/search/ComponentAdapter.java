package com.example.coenelec390.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.db_manager.Component;

import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ViewHolder> {
    private List<Component> components;

    public ComponentAdapter(List<Component> components) {
        this.components = components;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Component component = components.get(position);
        holder.bind(component);
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public void setComponents(List<Component> components) {
        this.components = components;
        notifyDataSetChanged();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize your views
        TextView mainCategoryTextView;
        TextView subCategoryTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find your views
            mainCategoryTextView = itemView.findViewById(R.id.mainCategoryTextView);
            subCategoryTextView = itemView.findViewById(R.id.subCategoryTextView);
        }

        void bind(Component component) {
            // Bind the data
            mainCategoryTextView.setText(component.getMainCategory());
            subCategoryTextView.setText(component.getSubCategory());
        }
    }
}
