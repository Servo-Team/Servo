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
import com.servo.utils.Hash;


/**
 * <h1>Login Servo</h1>
 * <p>
 *  Controller for Servo's
 *  login fragment
 * </p>
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 * @see     R.layout
 */
public class Login extends Fragment {

    private View globalView;
    private View dialogView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_login, container, false);

        //On to_register text click go to register page
        TextView loginToRegister = (TextView) globalView.findViewById(R.id.loginToRegister);
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToReg(view);
            }
        });

        //On login button click initate login proccess
        Button login_but = (Button) globalView.findViewById(R.id.loginButton);
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_user();
            }
        });
        return globalView;
    }

    /**
     * Main handler for login'g in users.
     * On success show a success dialog and
     * navigate to the homepage, otherwise
     * show a error and stay at the login page.
     *
     * @TODO remember_me option
     */
    private void login_user(){
        UserDatabase loginDB = new UserDatabase();
        final Activity act = getActivity();
        int user_id = 0;

        String username = ((EditText) globalView.findViewById(R.id.loginUser)).getText().toString();
        String password =  Hash.encodeBase64(((EditText) globalView.findViewById(R.id.loginPassword)).getText().toString());
        //Get the ID of user via
        //username and password
        try {
            user_id = loginDB.getIDUsername(username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //If user typed in email
        //instead of username
        if(user_id == -1){
            try {
                user_id = loginDB.getIDEmail(username,password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //If function getID() returned
        //-1 means it hasnt found a user
        Handler handler = new Handler(Looper.getMainLooper());
        if(user_id!=-1){
            ((MainActivity)act).main_dialog.startSuccessDialog("Logging in...");

            final int finalUser_id = user_id;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity)act).main_dialog.dismissDialog();
                    Bundle bundle = new Bundle();
                    bundle.putInt("USER_ID", finalUser_id);
                    ((MainActivity)act).navToHomePage(bundle);
                }
            }, 2500);
        } else{

            ((MainActivity)act).main_dialog.startErrorDialog("Invalid Username/Password");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity)act).main_dialog.dismissDialog();
                }
            }, 2500);
        }
    }

    /**
     * Simply navigates to
     * Servos register system
     * @param view
     */
    private void loginToReg(View view) {
        NavController ctrl = NavHostFragment.findNavController(this);
        ctrl.navigate(R.id.action_login_to_register);
    }
}