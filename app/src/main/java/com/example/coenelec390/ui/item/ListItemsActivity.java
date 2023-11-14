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
import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListItemsActivity extends androidx.fragment.app.DialogFragment {
    private RecyclerView recyclerView;
    private DatabaseReference categoryRef, subCategoryRef;
    private DatabaseManager databaseManager;
    private String selectedCategory;

    private ArrayAdapter<String> adapter;
    public ListItemsActivity(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootview = inflater.inflate(R.layout.list_items, container, false);

        recyclerView = rootview.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1);
//        recyclerView.setAdapter(adapter);

        return rootview;
    }

    public void fetchMainCategories(DatabaseManager.OnMainCategoriesLoadedListener listener){
        databaseManager.fetchMainCategories(new DatabaseManager.OnMainCategoriesLoadedListener() {
            @Override
            public void onMainCategoriesLoaded(List<String> mainCategories) {
                adapter.clear();
                adapter.addAll(mainCategories);
                listener.onMainCategoriesLoaded(mainCategories);
            }

            @Override
            public void onMainCategoriesError(String errorMessage) {
                listener.onMainCategoriesError(errorMessage);

            }
        });
    }

}
