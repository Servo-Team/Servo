package com.servo.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.servo.auth.R;
import com.servo.database.ServiceDatabase;
import com.servo.dialog.AddDialog;

import java.util.List;


public class AddFragment extends Fragment {

    private View globalView;
    private List<Object> services;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_add, container, false);

        FloatingActionButton fab = (FloatingActionButton) globalView.findViewById(R.id.fabAddProduct);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProduct();
            }
        });

        ServiceDatabase db = new ServiceDatabase();
        try {
            services = db.servicesRelated(((HomeActivity)getActivity()).USER_ID);
            if(services.isEmpty()){
                ((TextView)globalView.findViewById(R.id.noAddItemsText)).setAlpha(1.0f);
            } else{
                Log.i("SERVO", "NON-EMPTY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return globalView;
    }

    private void addProduct(){
        AddDialog dialog = new AddDialog(getActivity());
        dialog.startDialog();
    }
}