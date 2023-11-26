package com.example.coenelec390.ui.categories;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coenelec390.model.SubCategory;
import com.example.coenelec390.ui.categories.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.coenelec390.databinding.FragmentComponentNameBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ComponetNameAdapter extends RecyclerView.Adapter<ComponetNameAdapter.ViewHolder> {

//    private final List<PlaceholderItem> mValues;

    private ComponetNameAdapter.OnItemClickListener listener;
    private  List<String> componentNames;

    public ComponetNameAdapter(List<String> componentNames, ComponetNameAdapter.OnItemClickListener listener) {
        this.componentNames = componentNames;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentComponentNameBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String name= componentNames.get(position);
        //bind text
//        holder.subCategoryTextView.setText(name);
        holder.bind(name,position,listener);
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
    }

    public void setComponentNames(List<String> componentNames){
        this.componentNames = componentNames;
    }
    @Override
    public int getItemCount() {
        return componentNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentComponentNameBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        public void bind( String componentName,Integer position,ComponetNameAdapter.OnItemClickListener listener) {
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


    public interface OnItemClickListener {
        void onItemClick(String componentName);
    }

}