package com.example.coenelec390.ui.components;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;

import java.util.List;
import java.util.Map;

public class CharacteristicsAdapter extends RecyclerView.Adapter<CharacteristicsAdapter.ViewHolder> {
    // Define data for the adapter
    private final List<Map.Entry<String, Object>> characteristicsList;

    public CharacteristicsAdapter(List<Map.Entry<String, Object>> characteristicsList) {
        this.characteristicsList = characteristicsList;
    }

    // Define the view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView characteristicKeyTextView;
        private final TextView characteristicValueTextView;

        public ViewHolder(View view) {
            super(view);
            characteristicKeyTextView = view.findViewById(R.id.key);
            characteristicValueTextView = view.findViewById(R.id.value);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.characteristic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map.Entry<String, Object> characteristic = characteristicsList.get(position);
        holder.characteristicKeyTextView.setText(characteristic.getKey());
        holder.characteristicValueTextView.setText(characteristic.getValue().toString());
    }

    @Override
    public int getItemCount() {
        return characteristicsList.size();
    }
}
