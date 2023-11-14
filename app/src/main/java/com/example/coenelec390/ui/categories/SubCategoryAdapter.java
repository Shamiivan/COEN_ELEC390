package com.example.coenelec390.ui.categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private List<String> subCategories;
    private Context context;

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
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subCategoryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryTextView = itemView.findViewById(R.id.subCategoryTextView);
        }
    }
}
