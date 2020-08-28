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
import android.service.autofill.UserData;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.servo.utils.Constants;
import com.servo.utils.Hash;
import com.servo.utils.Image;
import com.servo.utils.RandomChooser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import java.net.URL;
import java.util.List;

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
    private      List<EditText> texts = new ArrayList();
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            checkFieldsForEmptyValues(texts);
        }
    };

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
                int status = 0;
                try {
                    status = register_user(view);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Handler handler = new Handler(Looper.getMainLooper());
                assert act != null;

                if(status == Constants.SUCCESS) {
                    ((MainActivity) act).main_dialog.startSuccessDialog("Successfully Registered User!");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity)act).main_dialog.dismissDialog();
                            navToLogin();
                        }
                    }, 2500);
                } else{
                    ((MainActivity) act).main_dialog.startErrorDialog("Must have a unique username!");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity)act).main_dialog.dismissDialog();
                        }
                    }, 3000);
                }

            }
        });
        disableRegister();

        return globalView;
    }

    /**
     * Main handler for
     * registering users.
     * Give them default values
     * for each value except filled out
     * forms(u-name,pass,etc...)
     * also checks all usernames are unique
     * @param view globalView
     * @return returns success if sucessfully
     *         registered failure otherwise
     */
    private int register_user(View view) throws UnsupportedEncodingException {

        UserDatabase registerDB = new UserDatabase();

        //User set all forms to user
        User user = new User();
        user.setUsername(((EditText)globalView.findViewById(R.id.registerUser)).getText().toString());
        user.setPassword(Hash.encodeBase64((((EditText)globalView.findViewById(R.id.registerPassword)).getText().toString())));
        user.setDOB(new Date((((EditText)globalView.findViewById(R.id.registerDOB)).getText().toString())));
        user.setPhone_NO(((EditText)globalView.findViewById(R.id.registerPhone)).getText().toString());
        user.setEmail(((EditText)globalView.findViewById(R.id.registerEmail)).getText().toString());

        try{
            if(registerDB.isUsernameUnique(user.getUsername()) == Constants.ERROR)
                return Constants.ERROR;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Set default avatar to the user
        try {
            File f = Image.convertUrlToFile(getContext(), RandomChooser.choose_image());
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

        return Constants.SUCCESS;
    }

    /**
     * Simply navigate to
     * login page
     */
    private void navToLogin() {
        NavController ctrl = NavHostFragment.findNavController(this);
        ctrl.navigate(R.id.action_register_to_login);
    }

    private void checkFieldsForEmptyValues(List<EditText> texts){
        Button b = (Button) globalView.findViewById(R.id.registerButton);
        b.setEnabled(true);
        b.setAlpha(1f);

        for(int i=0; i<texts.size(); i++){
            if(texts.get(i).getText().toString().isEmpty()){
                b.setEnabled(false);
                b.setAlpha(.5f);
                break;
            }
        }
    }

    private void disableRegister(){
        texts.add((((EditText)globalView.findViewById(R.id.registerUser))));
        texts.add((((EditText)globalView.findViewById(R.id.registerPassword))));
        texts.add((((EditText)globalView.findViewById(R.id.registerEmail))));
        texts.add((((EditText)globalView.findViewById(R.id.registerDOB))));
        texts.add((((EditText)globalView.findViewById(R.id.registerPhone))));
        for(int i=0; i<texts.size(); i++){
            texts.get(i).addTextChangedListener(textWatcher);
        }
        checkFieldsForEmptyValues(texts);
    }
}