package com.example.coenelec390.ui.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<String> mainCategories;
    private OnItemClickListener listener;

    public CategoryAdapter(List<String> mainCategories, OnItemClickListener listener) {
        this.mainCategories = mainCategories;
        this.listener = listener;
    }
    public void setMainCategories(List<String> mainCategories) {
        this.mainCategories = mainCategories;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_categories_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = mainCategories.get(position);
        holder.bind(category, listener);
    }

    @Override
    public int getItemCount() {
        return mainCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryTextView);
        }

        public void bind(String category, OnItemClickListener listener) {
            categoryName.setText(category);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(category);
                    Utils.print(category);
                }

            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String category);
    }

    // Add the following method
    public void clearData() {
        mainCategories.clear();
        notifyDataSetChanged();
    }
}
