package com.example.coenelec390.ui.item;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coenelec390.R;
import com.example.coenelec390.Utils;
import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;


public class AddItem extends androidx.fragment.app.DialogFragment {


    private EditText editText1, editText2 , editText3 , editText4 , editText5 , editText6 , editText7 ;
    String Name1 , Name2 , Name3 , Name4 , Name5 , Name6 , Name7 ;
    boolean nfcHere;
    DatabaseManager dbManager_for_add_items;
    private Button save ;
    //private Component comp;
    static String stringNFC;
    //IntentService IntentServiceContext = new IntentService();
    public int x;
    public AddItem thiss(){
        return this;
    }

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

        return rootview;
    }
}