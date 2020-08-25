package com.servo.auth;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
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


public class Login extends Fragment {

    private View globalView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_login, container, false);
        TextView loginToRegister = (TextView) globalView.findViewById(R.id.loginToRegister);
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToReg(view);
            }
        });

        Button login_but = (Button) globalView.findViewById(R.id.loginButton);
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_user();
            }
        });
        return globalView;
    }

    private void login_user(){
        UserDatabase loginDB = new UserDatabase();
        final Activity act = getActivity();
        int user_id = 0;
        try {
            String username = ((EditText) globalView.findViewById(R.id.loginUser)).getText().toString();
            String password = ((EditText) globalView.findViewById(R.id.loginPassword)).getText().toString();
            user_id = loginDB.getID(username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Handler handler = new Handler(Looper.getMainLooper());
        if(user_id!=-1){
            ((MainActivity)act).main_dialog.startSuccessDialog();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity)act).main_dialog.dismissDialog();
                }
            }, 2500);
        } else{
            ((MainActivity)act).main_dialog.startErrorDialog();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity)act).main_dialog.dismissDialog();
                }
            }, 2500);
        }
    }
    private void loginToReg(View view) {
        NavController ctrl = NavHostFragment.findNavController(this);
        ctrl.navigate(R.id.action_login_to_register);
    }
}