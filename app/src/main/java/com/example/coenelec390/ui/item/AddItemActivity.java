package com.example.coenelec390.ui.item;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coenelec390.R;
import com.example.coenelec390.db_manager.Component;
import com.example.coenelec390.db_manager.DatabaseManager;
import java.util.HashMap;
import java.util.Map;


public class AddItemActivity  extends AppCompatActivity{


    private EditText editText1, editText2 , editText3 , editText4 , editText5 , editText6 , editText7 ;
    String Name1 , Name2 , Name3 , Name4 , Name5 , Name6 , Name7 ;

    DatabaseManager dbManager_for_add_items;
    private Button save ;
    //private Component comp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);


        editText1 = findViewById(R.id.etId1);
        editText2 = findViewById(R.id.editTextText2);
        editText3 = findViewById(R.id.editTextText3);
        editText4 = findViewById(R.id.editTextText4);
        editText5 = findViewById(R.id.editTextText5);
        editText6 = findViewById(R.id.editTextText6);
        editText7 = findViewById(R.id.editTextText7);
        save = findViewById(R.id.btnAddProduct);
        Map<String, String> characteristics1 = new HashMap<>();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name1 = editText1.getText().toString();
                Name2 = editText2.getText().toString();
                Name3 = editText3.getText().toString();
                Name4 = editText1.getText().toString();
                Name5 = editText2.getText().toString();
                Name6 = editText3.getText().toString();
                Name7 = editText3.getText().toString();

                if (Name1.equals("") || Name2.equals("") || Name3.equals("") || Name4.equals("") || Name5.equals("") || Name6.equals("") || Name7.equals(""))
                    Toast.makeText(AddItemsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else{
                    characteristics1.put(Name1, Name2);
                    // Toast.makeText(AddItemsActivity.this, "char gd", Toast.LENGTH_SHORT).show();

                    int x = 10;
                    Component comp = new Component( characteristics1 , Name3 , x);
                    //Toast.makeText(AddItemsActivity.this, "comp gd", Toast.LENGTH_SHORT).show();

                    dbManager_for_add_items.addComponent(Name3, Name4, Name5,comp );

                    Toast.makeText(AddItemsActivity.this, "db gd", Toast.LENGTH_SHORT).show();





                }






            }
        });


    }
























}
