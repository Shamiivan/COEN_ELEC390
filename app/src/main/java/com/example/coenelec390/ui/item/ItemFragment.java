package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.coenelec390.bluetooth.BLE_MANAGER;
import com.example.coenelec390.databinding.FragmentItemBinding;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ItemFragment extends Fragment {

    private FragmentItemBinding binding;
    private BLE_MANAGER bleManager;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FragmentManager fragmentManager = getChildFragmentManager(); // Use your actual way to obtain the FragmentManager
        bleManager = new BLE_MANAGER(getActivity() , fragmentManager);

        Button add = binding.additem;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AddItemActivity a = new AddItemActivity();
                //a.show(getActivity().getSupportFragmentManager(), "MyFragment");
                DatabaseManager dbManager = new DatabaseManager();
                //String data = "anythingg2";
                //dbManager.findNFC(data)
                  //      .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    //        @Override
                      //      public void onComplete(@NonNull Task<Boolean> task) {
                        //        if (task.isSuccessful()) {
                          //          boolean typeExists = task.getResult();
                            //        if (typeExists) {
                                        // Type exists in the database, show a message
                              //          Toast.makeText(getActivity(), "Type exists!", Toast.LENGTH_SHORT).show();
                                //    } else {
                                        // Type doesn't exist in the database, show a message
                                  //      Toast.makeText(getActivity(), "Type doesn't exist.", Toast.LENGTH_SHORT).show();
                                        AddItemActivity fragment = new AddItemActivity() ;
                                        fragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");

                                    //}
                                //} else {
                                    // Handle any potential errors here
                                    //Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
                                //}
                         //   }
                        //});
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}