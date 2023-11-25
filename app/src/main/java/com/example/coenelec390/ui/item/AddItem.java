package com.example.coenelec390.ui.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends androidx.fragment.app.DialogFragment {
    private Spinner cat;
    private Spinner subCat;
    ArrayAdapter<CharSequence> adapter;
    static String stringNFC;
    private TextView nfc , total;
    String Name1 , Name2 , Name3 , Name4 , Name5 , Name6 , Name7 ;
    private EditText partName, location , stock , description , UnitPrice  ;




    public AddItem(){

    }

    public static AddItem newInstance(String tag) {
        AddItem fragment = new AddItem();
        Bundle args = new Bundle();
        args.putString("TAG", tag);
        fragment.setArguments(args);
        stringNFC = tag;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootview = inflater.inflate(R.layout.add_item, container, false);

        nfc = rootview.findViewById(R.id.tvNFCtag);
        if (stringNFC!=null)
            nfc.setText(stringNFC);

        partName = rootview.findViewById(R.id.etID);
        location = rootview.findViewById(R.id.etLocation);
        stock =  rootview.findViewById(R.id.etStock);
        description= rootview.findViewById(R.id.extraDesc);
        UnitPrice= rootview.findViewById(R.id.etUnitPrice);


        cat = rootview.findViewById(R.id.category);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cat.setAdapter(adapter);

        subCat = rootview.findViewById(R.id.subcategory);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.Subcategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        subCat.setAdapter(adapter);

        Button save = rootview.findViewById(R.id.btnAddProduct);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Name1 = partName.getText().toString();
                Name2 = location.getText().toString();
                Name3 = stock.getText().toString();
                Name4 = description.getText().toString();
                Name5 = UnitPrice.getText().toString();
                Name6 = cat.getSelectedItem().toString();
                Name7 = subCat.getSelectedItem().toString();
                if (Name1.equals("") || Name2.equals("") || Name3.equals("") || /*Name4.equals("") ||*/ Name5.equals("") || Name6.equals("") || stringNFC.equals("") || Name7.equals(""))
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                else{

                    // insert a new component in  the database
                    DatabaseManager dbManager = new DatabaseManager();
                    Map<String, String> characteristics5 = new HashMap<>();
                    //characteristics5.put("description", Name4);
                    double price = 10;
                    //Component capacitor1 = new Component(stringNFC,Name1, Name2 , Name3,price,Integer.parseInt(Name5), ,characteristics5,  );
// Passive Components

                    Map<String, Object> characteristics2 = new HashMap<>();
                    characteristics2.put("Grade", "AEC-Q200");
                    characteristics2.put("Tolerance", "1%");
                    characteristics2.put("Rating", "0.1 W");
                    characteristics2.put("Package", "0603");
                    Component component2 = new Component(stringNFC,"Passive", "Resistors", "CRCW060349R9FKTA", 100, 11, "49.9 ohms", characteristics2);
                    Map<String, Object> characteristics3 = new HashMap<>();
                    characteristics3.put("Description", Name4);
                    //characteristics3.put("Tolerance", "0.50%");
                    //characteristics3.put("Rating", "0.125 W");
                    //characteristics3.put("Package", "0805");
                    Component component3 = new Component(stringNFC, Name6, Name7, Name1, Integer.parseInt(Name5), Integer.parseInt(Name3), Name2, characteristics3);


                    //dbManager.addComponent(Name2, Name3, Name4, capacitor1);
                    //DatabaseManager.BooleanDataCallback
                    dbManager.findNFC(stringNFC)
                            .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                                @Override
                                public void onComplete(@NonNull Task<Boolean> task) {
                                    if (task.isSuccessful()) {
                                        boolean typeExists = task.getResult();
                                        if (typeExists) {
                                            // Type exists in the database, show a message
                                            Toast.makeText(getActivity(), "Type exists!", Toast.LENGTH_SHORT).show();

                                        } else {
                                            //editText7.setText(stringNFC);
                                            // Type doesn't exist in the database, show a message
                                            Toast.makeText(getActivity(), "Type doesn't exist.", Toast.LENGTH_SHORT).show();
                                            dbManager.addComponent(component3);
                                            Utils.print("db should be updated");
                                            Toast.makeText(getActivity(), "SUCCESSFULLY ADDED.", Toast.LENGTH_SHORT).show();


                                        }
                                    } else {
                                        // Handle any potential errors here
                                        Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    Toast.makeText(getActivity(), "nfcid= "+ stringNFC, Toast.LENGTH_SHORT).show();

                }
            }
        });

        return rootview;
    }
}