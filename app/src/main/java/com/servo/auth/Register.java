package com.servo.auth;


import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;

import com.servo.database.Database;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.utils.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import java.net.URL;

/**
 * <h1>Register Servo</h1>
 * <p>
 *  Controller for Servo's
 *  register fragment
 * </p>
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 * @see     R.layout
 */
public class Register extends Fragment {

    private View globalView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity act = getActivity();
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_register, container, false);

        //On to_login text click go to login page
        TextView registerToLogin = (TextView) globalView.findViewById(R.id.registerToLoginText);
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToLogin();
            }
        });

        //On register button click
        //register successfully
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

    /**
     * Main handler for
     * registering users.
     * Give them default values
     * for each value except filled out
     * forms(u-name,pass,etc...)
     * @TODO make all username's unique
     * @param view
     */
    private void register_user(View view) {

        Database registerDB = new UserDatabase();

        //User set all forms to user
        User user = new User();
        user.setUsername(((EditText)globalView.findViewById(R.id.registerUser)).getText().toString());
        user.setPassword(((EditText)globalView.findViewById(R.id.registerPassword)).getText().toString());
        user.setDOB(new Date((((EditText)globalView.findViewById(R.id.registerDOB)).getText().toString())));
        user.setPhone_NO(((EditText)globalView.findViewById(R.id.registerPhone)).getText().toString());
        user.setEmail(((EditText)globalView.findViewById(R.id.registerEmail)).getText().toString());


        //Set default avatar to the user
        try {
            File f = Image.convertUrlToFile(getContext(),"https://i.imgur.com/m95zDY8.png");
            user.setAvatar(f);
        } catch(IOException e){
            Log.e("SERVOERR", "DEFAULT AVATAR TRANSFORMATION ERROR : " + e.getMessage());
            e.printStackTrace();
        }

        user.setDescription("Its empty here...");
        user.setFollowing(0);
        user.setFollowers(0);

        //Finnaly insert user as a record
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

    /**
     * Simply navigate to
     * login page
     */
    private void navToLogin() {
        NavController ctrl = NavHostFragment.findNavController(this);
        ctrl.navigate(R.id.action_register_to_login);
    }
}