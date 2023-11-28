package com.example.coenelec390.ui.item;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.databinding.FragmentComponentListBinding;
import com.example.coenelec390.model.Component;
import com.example.coenelec390.ui.categories.SubCategoryAdapter;

import java.io.Serializable;
import java.util.List;

public class ComponentDetailFragment extends Fragment implements ComponentAdapter.OnItemClickListener  {
    private FragmentComponentListBinding binding;
    private List<Component> components;
    private SubCategoryAdapter adapter;
    private RecyclerView recyclerView;
    private Button editButton;

    public static ComponentDetailFragment newInstance(List<Component> components) {
        ComponentDetailFragment fragment = new ComponentDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("components", (Serializable) components);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            components = (List<Component>) getArguments().getSerializable("components");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentComponentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up the RecyclerView
        recyclerView = binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ComponentAdapter(components, this)); // You need to create this adapter
        //editButton = root.findViewById(R.id.editbutton);

        editButton = root.findViewById(R.id.editButton);
        /*editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*components.get(position);* /
            }
        });*/

        return root;
    }

    @Override
    public void onItemClick(Component component) {
        // Handle item click here
        openEditFragment(component);
    }

    private void openEditFragment(Component component) {
        // Create a new instance of FragmentEdit and pass the selected Component
       // FragmentEdit fragmentEdit = FragmentEdit.newInstance(component);

        // Use FragmentManager to replace the current fragment with FragmentEdit
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        /*fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentEdit)
                .addToBackStack(null)
                .commit();*/
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.print("Onpause called ");
        if (recyclerView.getAdapter() != null) {
            recyclerView.setAdapter(null);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d("FragmentLifecycle", "onStop called");
    }


    public interface OnBackPressedListener {
        void onBackPressed();
    }

    private OnBackPressedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnBackPressedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnBackPressedListener");
        }
    }

    public void handleBackPress() {
        if (mListener != null) {
            mListener.onBackPressed();
        }
    }


}
