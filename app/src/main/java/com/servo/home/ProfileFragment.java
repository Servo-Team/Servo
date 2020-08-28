package com.servo.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.servo.adapter.ProfileAdapter;
import com.servo.auth.MainActivity;
import com.servo.auth.R;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.utils.Constants;
import com.servo.utils.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProfileFragment extends Fragment {
    private View globalView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_profile, container, false);
        Activity act = getActivity();

        assert act!=null;
        ((TextView) globalView.findViewById(R.id.profilePageUsername)).setText(((HomeActivity)act).USER.getUsername());
        ((TextView) globalView.findViewById(R.id.profilePageDesc)).setText(((HomeActivity)act).USER.getDescription());

        UserDatabase db = new UserDatabase();
        File img = null;
        try {
            img = db.getAvatar(((HomeActivity)act).USER.getUsername(), getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath());
        ImageView image = (ImageView) globalView.findViewById(R.id.imageProfile);
        image.setImageBitmap(bitmap);


        setUpListView();

        return globalView;
    }

    private void setUpListView(){
        ListView listView = globalView.findViewById(R.id.profileListView);
        ProfileAdapter profileAdapter = new ProfileAdapter(getActivity(), Constants.profile_list_title, Constants.profile_list_image);
        listView.setAdapter(profileAdapter);
        listView.setDivider(null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}