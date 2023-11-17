package com.example.coenelec390.ui.notifications;

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
                if (bleManager.hasBluetooth())  bleManager.disableBluetooth();
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
                bleManager.connectPeripheral();
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

    public static void loadData() throws Exception {
        // Passive Components

        Map<String, Object> characteristics2 = new HashMap<>();
        characteristics2.put("Grade", "AEC-Q200");
        characteristics2.put("Tolerance", "1%");
        characteristics2.put("Rating", "0.1 W");
        characteristics2.put("Package", "0603");

        Component component2 = new Component("Passive", "Resistors", "CRCW060349R9FKTA", 100, 11, "49.9 ohms", characteristics2);

        Map<String, Object> characteristics3 = new HashMap<>();
        characteristics3.put("Grade", "AEC-Q200");
        characteristics3.put("Tolerance", "0.50%");
        characteristics3.put("Rating", "0.125 W");
        characteristics3.put("Package", "0805");

        Component component3 = new Component("Passive", "Resistors", "ERA-6AED333V", 50, 11, "33k ohms", characteristics3);

        Map<String, Object> characteristics4 = new HashMap<>();
        characteristics4.put("Grade", "AEC-Q200");
        characteristics4.put("Tolerance", "10%");
        characteristics4.put("Rating", "50 V");
        characteristics4.put("Package", "0603");

        Component component4 = new Component("Passive", "Capacitors", "06035C104K4Z4A", 2000, 44, "0.1 uF", characteristics4);

        Map<String, Object> characteristics5 = new HashMap<>();
        characteristics5.put("Grade", "AEC-Q200");
        characteristics5.put("Tolerance", "5%");
        characteristics5.put("Rating", "50 V");
        characteristics5.put("Package", "0603");

        Component component5 = new Component("Passive", "Capacitors", "C0603X220J5GACAUTO", 100, 3, "22 pF", characteristics5);

        Map<String, Object> characteristics6 = new HashMap<>();
        characteristics6.put("Grade", "AEC-Q200");
        characteristics6.put("Tolerance", "20%");
        characteristics6.put("Rating", "3.8 A");
        characteristics6.put("Package", "4040");

        Component component6 = new Component("Passive", "Inductors", "IHLP4040EDER220M5A", 20, 1, "22 uH", characteristics6);

        Map<String, Object> characteristics7 = new HashMap<>();
        characteristics7.put("Grade", "AEC-Q200");
        characteristics7.put("Tolerance", "20%");
        characteristics7.put("Rating", "4 A");
        characteristics7.put("Package", "N/S");

        Component component7 = new Component("Passive", "Inductors", "SRP5015TA-2R2M", 50, 100, "2.2 uH", characteristics7);

        Map<String, Object> characteristics8 = new HashMap<>();
        characteristics8.put("Grade", "ROHS II");
        characteristics8.put("Tolerance", "25%");
        characteristics8.put("Rating", "0.3 A");
        characteristics8.put("Package", "0805");

        Component component8 = new Component("Passive", "Ferrite Beads", "ACML-0805-102-T", 100, 100, "1k ohms", characteristics8);

        Map<String, Object> characteristics9 = new HashMap<>();
        characteristics9.put("Grade", "ROHS");
        characteristics9.put("Tolerance", "25%");
        characteristics9.put("Rating", "2 A");
        characteristics9.put("Package", "0805");

        Component component9 = new Component("Passive", "Ferrite Beads", "MH2029-101Y", 500, 100, "100 ohms", characteristics9);


        // Active Components
        Map<String, Object> characteristics10 = new HashMap<>();
        characteristics10.put("Type", "N-Channel");
        characteristics10.put("RoHS", "ROHS");
        characteristics10.put("Package", "SOT-23");
        characteristics10.put("Voltage", "60 V");

        Component component10 = new Component("Active", "MOSFETs", "2N7002K-TP", 20, 100, "N-Channel", characteristics10);

        Map<String, Object> characteristics11 = new HashMap<>();
        characteristics11.put("Type", "P-Channel");
        characteristics11.put("RoHS", "ROHS");
        characteristics11.put("Package", "8 SO");
        characteristics11.put("Voltage", "30 V");

        Component component11 = new Component("Active", "MOSFETs", "IRF7424", 25, 5, "P-Channel", characteristics11);

        Map<String, Object> characteristics12 = new HashMap<>();
        characteristics12.put("Type", "Arm Cortex M7");
        characteristics12.put("Family", "RT");
        characteristics12.put("Package", "LQFP144");
        characteristics12.put("Voltage", "3-3.6 V");

        Component component12 = new Component("Active", "MCUs", "SAMV71Q21RT", 5, 65, "Arm Cortex M7", characteristics12);

        Map<String, Object> characteristics13 = new HashMap<>();
        characteristics13.put("Type", "RISC");
        characteristics13.put("Family", "FH");
        characteristics13.put("Package", "LQFP-64");
        characteristics13.put("Voltage", "1.8-3.6 V");

        Component component13 = new Component("Active", "MCUs", "MSP430FR5989", 10, 11, "RISC", characteristics13);

        Map<String, Object> characteristics14 = new HashMap<>();
        characteristics14.put("Type", "NPN");
        characteristics14.put("Grade", "AEC-Q100");
        characteristics14.put("Package", "SOT-23-3");
        characteristics14.put("Voltage", "40 V");

        Component component14 = new Component("Active", "BJTs", "MMBT3904-7-F", 150, 11, "NPN", characteristics14);

        Map<String, Object> characteristics15 = new HashMap<>();
        characteristics15.put("Type", "PNP");
        characteristics15.put("Application", "Automotive");
        characteristics15.put("Package", "SOT223");
        characteristics15.put("Voltage", "25 V");

        Component component15 = new Component("Active", "BJTs", "FZT749", 75, 45, "PNP", characteristics15);

        Map<String, Object> characteristics16 = new HashMap<>();
        characteristics16.put("Type", "Current Sense");
        characteristics16.put("Application", "Automotive");
        characteristics16.put("Package", "8 SO");
        characteristics16.put("Voltage", "-4 to 80 V");

        Component component16 = new Component("Active", "Op-amps", "INA240A3EDRQ1", 15, 4, "Current Sense", characteristics16);

        Map<String, Object> characteristics17 = new HashMap<>();
        characteristics17.put("Type", "General Purpose");
        characteristics17.put("Application", "N/A");
        characteristics17.put("Package", "8 SO");
        characteristics17.put("Voltage", "3 to 30 V");

        Component component17 = new Component("Active", "Op-amps", "LM158DT", 1.42, 20, "General Purpose", characteristics17);

        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addComponent(component2);
        databaseManager.addComponent(component3);
        databaseManager.addComponent(component4);
        databaseManager.addComponent(component5);
        databaseManager.addComponent(component6);
        databaseManager.addComponent(component7);
        databaseManager.addComponent(component8);
        databaseManager.addComponent(component9);
        databaseManager.addComponent(component10);
        databaseManager.addComponent(component11);
        databaseManager.addComponent(component12);
        databaseManager.addComponent(component13);
        databaseManager.addComponent(component14);
        databaseManager.addComponent(component15);
        databaseManager.addComponent(component16);
        databaseManager.addComponent(component17);

    }
}