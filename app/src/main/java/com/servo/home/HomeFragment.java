package com.servo.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.servo.adapter.ServiceAdapter;
import com.servo.auth.MainActivity;
import com.servo.auth.R;
import com.servo.database.Service;
import com.servo.database.ServiceDatabase;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.utils.StringManupilation;
import com.servo.view.ServiceModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {
    private View globalView;
    private List<List<Object>> services;

    private RecyclerView recyclerView;
    private ServiceAdapter adapter;

    private Activity act;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView = inflater.inflate(R.layout.fragment_home, container, false);
        services = new ArrayList<List<Object>>();

        act = getActivity();
        User user = ((HomeActivity)act).USER;
        assert act!=null;
        TextView userText = (TextView) globalView.findViewById(R.id.welcomeUsername);

        userText.setText(user.getUsername());

        if(user.getFollowing() == 0){
            globalView.findViewById(R.id.noFollowingText).setAlpha(1);
        } else{

            //            ServiceDatabase db = new ServiceDatabase();
            try {
                //Cards of services
                recyclerView = globalView.findViewById(R.id.homeRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new ServiceAdapter(getContext(), getServices(), getActivity(), true);
                recyclerView.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return globalView;
    }

    private List<ServiceModel> getServices() throws Exception{
        UserDatabase db = new UserDatabase();
        ServiceDatabase serviceDB = new ServiceDatabase();
        List<Integer> ids = db.getFollowingIDS(((HomeActivity)act).USER.getUsername());

        for(Integer id: ids){
            System.out.println("IDSSSSSSSSSSSSSSSSSSSSSSSS: " + Integer.toString(id));
            services.add(serviceDB.servicesRelated(id));
        }

        List<ServiceModel> models = new ArrayList();

        for(int i=0; i<services.size(); i++) {
            for(int j=0; j<services.get(i).size(); j++){
                ServiceModel model = new ServiceModel();
                Service service_inst = (Service) services.get(i).get(j);
                model.setTitle(service_inst.getTitle());
                model.setDesc(service_inst.getDescription());
                model.setChipText(StringManupilation.explode_chips(service_inst.getTags()));
                model.setServiceID(service_inst.getID());
                model.setFinished(new ServiceDatabase().isFinished(service_inst.getID()));

                models.add(model);
            }
        }

        return models;
    }
}