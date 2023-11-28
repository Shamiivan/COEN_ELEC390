package com.example.coenelec390.ui.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mainCategories;
    private final OnItemClickListener listener;

    public CategoryAdapter(List<Category> mainCategories, OnItemClickListener listener) {
        this.mainCategories = mainCategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categories_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mainCategories.get(position);
        holder.bind(category, listener);
    }

    @Override
    public int getItemCount() {
        return mainCategories == null ? 0 : mainCategories.size();
    }

    public void setMainCategories(List<Category> mainCategories) {
        this.mainCategories = mainCategories;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(String category);
    }
    public void clearData() {
        mainCategories.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryName;
        //todo : include child count

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryTextView);
        }

        public void bind(Category category, OnItemClickListener listener) {
            String name = category.getName();
            long childCount = category.getChildCount();

            categoryName.setText(name);
            itemView.setOnClickListener(v -> {
                listener.onItemClick(name);
            });
        }
    }

}
