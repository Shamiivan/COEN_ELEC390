package com.example.coenelec390.ui.item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.coenelec390.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListItemsActivity extends androidx.fragment.app.DialogFragment {
    private RecyclerView recyclerView;
    private DatabaseReference componentsRef, idRef;

    public ListItemsActivity(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootview = inflater.inflate(R.layout.list_items, container, false);

        recyclerView = rootview.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        componentsRef = FirebaseDatabase.getInstance().getReference().child("components");
        idRef = FirebaseDatabase.getInstance().getReference().child("type");

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(componentsRef, Item.class).build();

        FirebaseRecyclerAdapter<Item, ItemsViewHolder> adapter = new FirebaseRecyclerAdapter<Item, ItemsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemsViewHolder holder, int position, @NonNull Item model) {
                String componentIDs = getRef(position).getKey();

                idRef.child(componentIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String idview = snapshot.child("type").getValue().toString();
                        String locationview = snapshot.child("location").getValue().toString();
                        String stockview = snapshot.child("quantity").getValue().toString();

                        holder.id.setText(idview);
                        holder.location.setText(locationview);
                        holder.stock.setText(stockview);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
                ItemsViewHolder viewHolder = new ItemsViewHolder(view);

                return viewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder{
        TextView id, location, stock;
        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tvID);
            location = itemView.findViewById(R.id.tvLocation);
            stock = itemView.findViewById(R.id.tvStock);
        }
    }
}
