package com.example.coenelec390.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.coenelec390.databinding.FragmentBluetoothBinding;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.model.Component;

public class NotificationsFragment extends Fragment implements BLE_MANAGER.FragmentOpener {

    private FragmentBluetoothBinding binding;
    public BLE_MANAGER bleManager;
    private BleViewModel viewModel;
    @Override
    public void openFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, fragment)
                .commit();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BleViewModel.class);

        binding = FragmentBluetoothBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentManager fragmentManager = getChildFragmentManager(); // Use your actual way to obtain the FragmentManager
        bleManager = new BLE_MANAGER(getActivity() , fragmentManager, R.id.nav_host_fragment_activity_main);
        bleManager.setFragmentOpener(this);
        TextView bluetoothStateTextView = root.findViewById(R.id.bleStatus);
        bleManager.setBluetoothDataListener(new BluetoothDataListener() {
            @Override
            public void onBluetoothDataReceived(String data) {
                viewModel.setBluetoothData(data);
            }
        });

        viewModel.getBluetoothData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                bluetoothStateTextView.setText(s);
            }
        });

        Button btnScan = root.findViewById(R.id.scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bleManager.hasBluetooth())  bleManager.enableBluetooth();
                bleManager.startScan();
                // Use a Handler to delay the stopScan and subsequent actions
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bleManager.stopScan();
                        bleManager.showDevices();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            bleManager.connectPeripheral();
                        }
                    }
                }, 5000);
            }
        });


        Button signout = root.findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , SignInActivity.class);
                startActivity(intent);
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