package com.example.coenelec390.bluetooth;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.coenelec390.MainActivity;
import com.example.coenelec390.Utils;
import com.example.coenelec390.db_manager.DatabaseManager;
import com.example.coenelec390.ui.item.AddItemActivity;
import com.example.coenelec390.ui.item.ItemFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class IntentService extends JobIntentService {

    // Unique job ID for this service
    private static final int JOB_ID = 1000;
    //Context context;
    //AddItemActivity itemFragment;
    private FragmentManager fragmentManager ;

    private FragmentOpenListener fragmentOpenListener;
    public void setActivityListener(FragmentOpenListener activityListener) {
        this.fragmentOpenListener = activityListener;
    }

//    public IntentService(FragmentOpenListener listener) {
//        this.fragmentOpenListener = listener;
//    }

    public IntentService(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
    public IntentService( ) {
    }
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, IntentService.class, JOB_ID, work);

    }

    public void openYourFragment(String data) {
        // Create your fragment instance
        AddItemActivity fragment = AddItemActivity.newInstance(data);

        // Notify the listener to open the fragment
        if (fragmentOpenListener != null) {
            fragmentOpenListener.onOpenFragment();
        }
    }



    @Override
    protected void onHandleWork( Intent intent) {
        // Handle the work in the background
        if (intent.getAction() != null && intent.getAction().equals("com.example.coenelec390.bluetooth.NEW_CHARACTERISTIC")) {
            String data = intent.getStringExtra("data");
            Utils.print("I think it's broadcasting to intent");

            DatabaseManager dbManager = new DatabaseManager();
            dbManager.findNFC(data)
                    .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                boolean typeExists = task.getResult();
                                if (typeExists) {
                                    // Type exists in the database, show a message
                                    //Toast.makeText(getActivity(), "Type exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Type doesn't exist in the database, show a message. go to op
                                    //Toast.makeText(getActivity(), "Type doesn't exist.", Toast.LENGTH_SHORT).show();
                                    //MainActivity main = (MainActivity) context;
                                    //FragmentManager fragmentManager1 = fragment.getFragmentManager();
                                    //FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();
                                    AddItemActivity fragment = AddItemActivity.newInstance(data);
                                    if(fragmentOpenListener!=null)
                                        fragmentOpenListener.onOpenFragment();
                                    fragment.show(fragmentManager, "dialogFragment");
                                    //openYourFragment(data);
                                    //if (!fragment.isAdded())
                                    //fragment.show(fragmentManager, "dialogFragment");


                                    //Intent intent = new Intent(context , fragment.());
                                    //startActivity(intent);

                                }
                            } else {
                                // Handle any potential errors here
                                //Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
    }
}

