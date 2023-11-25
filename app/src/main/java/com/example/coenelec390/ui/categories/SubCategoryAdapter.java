package com.example.coenelec390.ui.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.model.Component;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private String MainCategory;
    private SubCategoryAdapter.OnItemClickListener listener;
    private List<String> subCategories;
    private Context context;

    private List<Component> components;
    public SubCategoryAdapter(List<String> subCategories, Context context, OnItemClickListener listener) {
        this.subCategories = subCategories;
        this.listener = listener;
    }
    public SubCategoryAdapter(List<String> subCategories, Context context) {
        this.subCategories = subCategories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String subCategory = subCategories.get(position);
        holder.subCategoryTextView.setText(subCategory);
        holder.bind(subCategory, listener);
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subCategoryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryTextView = itemView.findViewById(R.id.textView);
        }
        public void bind(String subCategory, SubCategoryAdapter.OnItemClickListener listener) {
            subCategoryTextView.setText(subCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(subCategory);
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


