package com.servo.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.servo.adapter.ServiceAdapter;
import com.servo.auth.R;
import com.servo.database.Service;
import com.servo.database.ServiceDatabase;
import com.servo.dialog.AddDialog;
import com.servo.utils.StringManupilation;
import com.servo.view.ServiceModel;

import java.util.ArrayList;
import java.util.List;


public class AddFragment extends Fragment {

    private View globalView;
    private List<Object> services;

    private RecyclerView recyclerView;
    private ServiceAdapter adapter;

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
                //Cards of services
                recyclerView = globalView.findViewById(R.id.addRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new ServiceAdapter(getContext(), getServices(), getActivity());
                recyclerView.setAdapter(adapter);
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

    private List<ServiceModel> getServices(){
        List<ServiceModel> model = new ArrayList();

        for(int i=0; i<services.size(); i++){
            ServiceModel service_inst = new ServiceModel();
            Service service_main = (Service) services.get(i);

            service_inst.setTitle(service_main.getTitle());
            service_inst.setDesc(service_main.getDescription());
            service_inst.setChipText(StringManupilation.explode_chips(service_main.getTags()));

            model.add(service_inst);
        }

        return model;
    }

}