package com.servo.auth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.utils.Constants;
import com.servo.utils.Hash;
import com.servo.utils.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;


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
    private Activity act;
    private CallbackManager callbackManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView =  inflater.inflate(R.layout.fragment_login, container, false);
        act = getActivity();

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

        FloatingActionButton google_but = (FloatingActionButton) globalView.findViewById(R.id.googleButton);
        google_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_google();
            }
        });


        /**
         * @TODO FINISH FACEBOOK LOGIN
         */
        callbackManager = CallbackManager.Factory.create();

        FloatingActionButton facebook_fab = (FloatingActionButton) globalView.findViewById(R.id.facebookButtonLogin);
        facebook_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_facebook();
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
        int user_id = 0;

        String username = ((EditText) globalView.findViewById(R.id.loginUser)).getText().toString();
        String password = Hash.encodeBase64(((EditText) globalView.findViewById(R.id.loginPassword)).getText().toString());

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
                user_id = loginDB.getIDEmail(username,password); //username as "email"
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

    private void login_google(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try{
            
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            UserDatabase loginDB = new UserDatabase();
            int user_id = -1;

            //Get the ID of user via
            //username and password
            try {
                user_id = loginDB.getIDUsername(acct.getDisplayName(),acct.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

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

                ((MainActivity)act).main_dialog.startErrorDialog("Must sign up first!");

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)act).main_dialog.dismissDialog();
                    }
                }, 2500);
            }
            
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SERVO", "signInResult:failed code=" + e.getStatusCode());
        }
    }



    private void custom_facebook(){
        Toast.makeText(getActivity(), "Facebook Login Not Implemented Yet", Toast.LENGTH_LONG);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        System.out.println("SUCCESS");
                       // Log.i("FACEBOOK", loginResult.getAccessToken().getUserId());

                    }

                    @Override
                    public void onCancel(){
                        System.out.println("CANCEL");
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception){
                        System.out.println("ERROR");
                        // App code
                    }
                });

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