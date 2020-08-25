package com.servo.auth;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.servo.database.Database;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.utils.Constants;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import java.net.URL;


public class Register extends Fragment {

    private View globalView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity act = getActivity();
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_register, container, false);

        TextView registerToLogin = (TextView) globalView.findViewById(R.id.registerToLoginText);
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToLogin();
            }
        });

        Button registerUserButton = (Button) globalView.findViewById(R.id.registerButton);


        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_user(view);
                Handler handler = new Handler(Looper.getMainLooper());
                ((MainActivity)act).main_dialog.startSuccessDialog();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)act).main_dialog.dismissDialog();
                        navToLogin();
                    }
                }, 2500);

            }
        });

        return globalView;
    }

    private void register_user(View view) {

        Database registerDB = new UserDatabase();

        User user = new User();
        user.setUsername(((EditText)globalView.findViewById(R.id.registerUser)).getText().toString());
        user.setPassword(((EditText)globalView.findViewById(R.id.registerPassword)).getText().toString());
        user.setDOB(new Date((((EditText)globalView.findViewById(R.id.registerDOB)).getText().toString())));
        user.setPhone_NO(((EditText)globalView.findViewById(R.id.registerPhone)).getText().toString());
        user.setEmail(((EditText)globalView.findViewById(R.id.registerEmail)).getText().toString());


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Bitmap image = null;
            try {
                URL url = new URL("https://i.imgur.com/m95zDY8.png");
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(IOException e) {
                System.out.println(e);
            }

            //create a file to write bitmap data
            File f = new File(getContext().getCacheDir(), "avatar");
            f.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            user.setAvatar(f);

        } catch(IOException e){
            Log.e("SERVOERR", "DEFAULT AVATAR TRANSFORMATION ERROR : " + e.getMessage());
            e.printStackTrace();
        }

        user.setDescription("Its empty here...");
        user.setFollowing(0);
        user.setFollowers(0);

        try {
            registerDB.insertObj(user);
        } catch (SQLException e) {
            Log.e("SERVOERR", "INSERTING USER ERROR : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("SERVOERR", "INSERTING USER ERROR : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void navToLogin() {
        NavController ctrl = NavHostFragment.findNavController(this);
        ctrl.navigate(R.id.action_register_to_login);
    }
}