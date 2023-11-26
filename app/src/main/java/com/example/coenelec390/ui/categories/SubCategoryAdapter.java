package com.example.coenelec390.ui.categories;

import android.content.Context;
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

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private String MainCategory;
    private SubCategoryAdapter.OnItemClickListener listener;
    private List<Category> subCategories;

    private List<Component> components;
    public SubCategoryAdapter(List<Category> subCategories, OnItemClickListener listener) {
        this.subCategories = subCategories;
        this.listener = listener;
    }
    public SubCategoryAdapter(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category subCategory = subCategories.get(position);
        String name = subCategory.getName();
        //bind text
        //todo : set the part number count
//        holder.subCategoryTextView.setText(name);
        holder.bind(subCategory, listener);
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subCategoryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryTextView = itemView.findViewById(R.id.textView);
        }
        public void bind(Category subCategory, SubCategoryAdapter.OnItemClickListener listener) {
            String name = subCategory.getName();
            subCategoryTextView.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(name);
                }

            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String componentName);
    }

    public void clearData() {
        subCategories.clear();
        notifyDataSetChanged();
    }
}


