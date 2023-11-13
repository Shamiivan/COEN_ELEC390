package com.example.coenelec390.ui.item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.coenelec390.MainActivity;
import com.example.coenelec390.R;
import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddItemActivity  extends androidx.fragment.app.DialogFragment {


    private EditText editText1, editText2 , editText3 , editText4 , editText5 , editText6 , editText7 ;
    String Name1 , Name2 , Name3 , Name4 , Name5 , Name6 , Name7 ;
    boolean nfcHere;
    DatabaseManager dbManager_for_add_items;
    private Button save ;
    //private Component comp;
    static String stringNFC;


    public AddItemActivity(){

    }

    public static AddItemActivity newInstance(String tag) {
        AddItemActivity fragment = new AddItemActivity();
        Bundle args = new Bundle();
        args.putString("TAG", tag);
        fragment.setArguments(args);
        stringNFC = tag;
        return fragment;
    }
    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("TAG");
        // Create your dialog with the message
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        // Set other dialog properties
        return builder.create();
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View rootview = inflater.inflate(R.layout.activity_add_item, container, false);

        editText1 = rootview.findViewById(R.id.etId1);
        editText2 = rootview.findViewById(R.id.etImageURL1);
        editText3 = rootview.findViewById(R.id.etStock1);
        editText4 = rootview.findViewById(R.id.etDescription1);
        //editText5 = findViewById(R.id.editTextText5);
        //editText6 = findViewById(R.id.editTextText6);
        //editText7 = findViewById(R.id.editTextText7);
        save = rootview.findViewById(R.id.btnAddProduct);
        Map<String, String> characteristics1 = new HashMap<>();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name1 = editText1.getText().toString();
                //Name2 = editText2.getText().toString();
                //Name3 = editText3.getText().toString();
                //Name4 = editText4.getText().toString();
                //Name5 = editText2.getText().toString();
                //Name6 = editText3.getText().toString();
                //Name7 = editText3.getText().toString();
                if (Name1.equals("") /*|| Name2.equals("") || Name3.equals("") || Name4.equals("") || Name5.equals("") || Name6.equals("") || Name7.equals("")*/)
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                else{
                    DatabaseManager dbManager = new DatabaseManager();
                    Map<String, String> characteristics5 = new HashMap<>();
                    characteristics5.put("capacitance", "100uF");
                    Component capacitor1 = new Component(characteristics5, Name1, 100);
                    //dbManager.addComponent(Name2, Name3, Name4, capacitor1);
                    //DatabaseManager.BooleanDataCallback
                    dbManager.findNFC(Name1)
                            .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                                @Override
                                public void onComplete(@NonNull Task<Boolean> task) {
                                    if (task.isSuccessful()) {
                                        boolean typeExists = task.getResult();
                                        if (typeExists) {
                                            // Type exists in the database, show a message
                                            Toast.makeText(getActivity(), "Type exists!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Type doesn't exist in the database, show a message
                                            Toast.makeText(getActivity(), "Type doesn't exist.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Handle any potential errors here
                                        Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                   /*boolean x = dbManager.isTagNew(Name1);
                    if(x)
                        Toast.makeText(getActivity(), "new tag", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "old tag old", Toast.LENGTH_SHORT).show();*/


                    editText2.setText(stringNFC);
                    Toast.makeText(getActivity(), "nfcid= "+ stringNFC, Toast.LENGTH_SHORT).show();
                    //characteristics1.put(Name1, Name2);
                    // Toast.makeText(AddItemsActivity.this, "char gd", Toast.LENGTH_SHORT).show();
                    //int x = 10;
                    //Component comp = new Component( characteristics1 , Name3 , x);
                    //Toast.makeText(AddItemsActivity.this, "comp gd", Toast.LENGTH_SHORT).show();
                    //dbManager_for_add_items.addComponent(Name3, Name4, Name5,comp );
                    //Toast.makeText(AddItemActivity.this, "db gd", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return rootview;
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //editText1 = findViewById(R.id.etId1);
        //editText2 = findViewById(R.id.etImageURL1);
        //editText3 = findViewById(R.id.etStock1);
        //editText4 = findViewById(R.id.etDescription1);
        //editText5 = findViewById(R.id.editTextText5);
        //editText6 = findViewById(R.id.editTextText6);
        //editText7 = findViewById(R.id.editTextText7);
        //save = findViewById(R.id.btnAddProduct);
        Map<String, String> characteristics1 = new HashMap<>();
        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name1 = editText1.getText().toString();
                Name2 = editText2.getText().toString();
                Name3 = editText3.getText().toString();
                Name4 = editText4.getText().toString();
                //Name5 = editText2.getText().toString();
                //Name6 = editText3.getText().toString();
                //Name7 = editText3.getText().toString();
                if (Name1.equals("") || Name2.equals("") || Name3.equals("") || Name4.equals("") /*|| Name5.equals("") || Name6.equals("") || Name7.equals("")* /)
                    Toast.makeText(AddItemActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else{
                    DatabaseManager dbManager = new DatabaseManager();
                    Map<String, String> characteristics5 = new HashMap<>();
                    characteristics5.put("capacitance", "100uF");
                    Component capacitor1 = new Component(characteristics5, Name1, 100);
                    dbManager.addComponent(Name2, Name3, Name4, capacitor1);
                    Toast.makeText(AddItemActivity.this, "DB WORKED", Toast.LENGTH_SHORT).show();
                    //characteristics1.put(Name1, Name2);
                    // Toast.makeText(AddItemsActivity.this, "char gd", Toast.LENGTH_SHORT).show();
                    //int x = 10;
                    //Component comp = new Component( characteristics1 , Name3 , x);
                    //Toast.makeText(AddItemsActivity.this, "comp gd", Toast.LENGTH_SHORT).show();
                    //dbManager_for_add_items.addComponent(Name3, Name4, Name5,comp );
                    //Toast.makeText(AddItemActivity.this, "db gd", Toast.LENGTH_SHORT).show();
                }
            }
        });* /


    }*/
























}
