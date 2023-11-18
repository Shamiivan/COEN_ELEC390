package com.example.coenelec390.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.bluetooth.BLE_MANAGER;
import com.example.coenelec390.databinding.FragmentNotificationsBinding;
import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;

import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private BLE_MANAGER bleManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentManager fragmentManager = getChildFragmentManager(); // Use your actual way to obtain the FragmentManager
        bleManager = new BLE_MANAGER(getActivity() , fragmentManager);

        Button btnOn = root.findViewById(R.id.btnOn);
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bleManager.hasBluetooth())  bleManager.enableBluetooth();
            }
        });

        Button btnOff = root.findViewById(R.id.btnOff);
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = "00100000100";
                DatabaseManager manager = new DatabaseManager();
                manager.fetchComponent(tag,new  DatabaseManager.OnSpecificComponentLoadedListener(){

                    @Override
                    public void onSpecificComponent(Component component) {
                        component.display();
                    }

                    @Override
                    public void onSpecificComponentError(String errorMessage) {
                        Utils.print(errorMessage);
                    }
                });
            }

        });
        Button btnScan = root.findViewById(R.id.scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bleManager.hasBluetooth())  bleManager.enableBluetooth();
                bleManager.startScan();
            }
        });


        Button stopScan = root.findViewById(R.id.stopScan);
        stopScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bleManager.stopScan();
                bleManager.showDevices();
            }
        });

        Button connect = root.findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bleManager.connectPeripheral();
                }
            }
        });

        Button db = root.findViewById(R.id.updateDB);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}