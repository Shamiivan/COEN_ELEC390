package com.example.coenelec390.ui.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.model.Category;
import com.example.coenelec390.model.Component;

import java.util.List;

public class ComponentNumberAdapter extends RecyclerView.Adapter<ComponentNumberAdapter.ViewHolder> {
    private String mainCategory;
    private String subCategory;
    private ComponentNumberAdapter.OnItemClickListener listener;
    private List<String> componentNames;

    private List<Component> components;
    public ComponentNumberAdapter(List<String> componentNames, OnItemClickListener listener) {
        this.componentNames = componentNames;
        this.listener = listener;
    }
    public ComponentNumberAdapter(List<String> componentNames) {
        this.componentNames = componentNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String componentName = componentNames.get(position);
        //bind text
        //todo : set the part number count
//        holder.subCategoryTextView.setText(name);
        holder.bind(componentName, listener);
    }

    @Override
    public int getItemCount() {
        return componentNames.size();
    }

    public void setComponentNames(List<String> getComponentNames) {
        this.componentNames = componentNames;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subCategoryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryTextView = itemView.findViewById(R.id.textView);
        }
        public void bind(String componentName, ComponentNumberAdapter.OnItemClickListener listener) {
            subCategoryTextView.setText(componentName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(componentName);
                }

            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String componentName);
    }

    public void clearData() {
        componentNames.clear();
        notifyDataSetChanged();
    }
}


