package com.example.coenelec390.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.Utils;
import com.example.coenelec390.databinding.FragmentSearchListBinding;
import com.example.coenelec390.ui.categories.ComponetNameAdapter;
import com.example.coenelec390.ui.categories.placeholder.PlaceholderContent;

import java.util.List;

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private SearchAdapter.OnItemClickListener listener;
    private  List<String> componentNames;

    public SearchAdapter(List<String> componentNames, SearchAdapter.OnItemClickListener listener) {
        this.componentNames = componentNames;
        this.listener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchAdapter.ViewHolder(FragmentSearchListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = componentNames.get(position);
        holder.bind(name, position, listener);
    }

    @Override
    public int getItemCount() {
        return componentNames.size();
    }



    public void setComponentNames(List<String> componentNames){
        this.componentNames = componentNames;
    }

public interface OnItemClickListener {
    void onItemClick(String componentName);
}




    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderContent.PlaceholderItem mItem;

        public ViewHolder(@NonNull FragmentSearchListBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        public void bind( String componentName,Integer position,SearchAdapter.OnItemClickListener listener) {
            Utils.print(componentName);
            mContentView.setText(componentName);
            mIdView.setText(position.toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(componentName);
                }

            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }
}

