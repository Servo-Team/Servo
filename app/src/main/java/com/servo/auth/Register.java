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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.servo.utils.RandomChooser;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

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
    private      List<EditText> texts   = new ArrayList();
    private int google_sign_in_status   = Constants.SUCCESS;
    private int facebook_sign_in_status = Constants.SUCCESS;
    private Activity act;
    private CallbackManager callbackManager;

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
        act = getActivity();
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


        FloatingActionButton google_fab = (FloatingActionButton) globalView.findViewById(R.id.googleButton);
        google_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_google();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        FloatingActionButton facebook_fab = (FloatingActionButton) globalView.findViewById(R.id.facebookButton);
        facebook_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_facebook();
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

        user.setDescription(String.format("Its empty here... %s has not set his/her description.", user.getUsername()));
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

    private void register_google(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    private void custom_facebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "public_profile", "user_posts", "user_videos", "user_birthday", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {

                                        // Application code
                                        try {

                                            String id = object.getString("id");
                                            String first_name = object.getString("first_name");
                                            String last_name = object.getString("last_name");
                                            String birthday = object.getString("birthday");
                                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
                                            String email = "N/A";
                                            if (object.has("email")) {
                                                email = object.getString("email");
                                            }

                                            User user = new User();
                                            user.setUsername(first_name + " " + last_name);
                                            user.setPassword(id);
                                            user.setEmail(email);
                                            user.setPhone_NO("9999999999");
                                            user.setDOB(new Date(birthday));
                                            user.setFollowers(0);
                                            user.setFollowing(0);
                                            user.setDescription(String.format("Its empty here... %s has not set his/her description.", user.getUsername()));

                                            Uri profilePictureUri = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 512 , 512 );
                                            String urlPfp = profilePictureUri.toString();
                                            File f = Image.convertUrlToFile(getContext(), urlPfp);
                                            user.setAvatar(f);

                                            UserDatabase db = new UserDatabase();
                                            try{
                                                if(db.isUsernameUnique(user.getUsername()) == Constants.ERROR){
                                                    facebook_sign_in_status = Constants.ERROR;
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            if(facebook_sign_in_status == Constants.SUCCESS) db.insertObj(user);

                                            Handler handler = new Handler(Looper.getMainLooper());
                                            assert act != null;

                                            if(facebook_sign_in_status == Constants.SUCCESS) {
                                                ((MainActivity) act).main_dialog.startSuccessDialog("Successfully Registered User!");
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ((MainActivity)act).main_dialog.dismissDialog();
                                                        navToLogin();
                                                    }
                                                }, 2500);
                                            } else{
                                                ((MainActivity) act).main_dialog.startErrorDialog("Facebook duplicate usernames!");
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ((MainActivity)act).main_dialog.dismissDialog();
                                                    }
                                                }, 5000);
                                            }


                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
                        request.setParameters(parameters);
                        request.executeAsync();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                handleRegister(task);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRegister(Task<GoogleSignInAccount> completedTask) throws Exception {
        try {
            UserDatabase db = new UserDatabase();
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);

            Uri personPhoto = acct.getPhotoUrl();
            User user = new User();
            user.setUsername(acct.getDisplayName());
            user.setPassword(acct.getId());
            user.setEmail(acct.getEmail());
            user.setPhone_NO("9999999999");
            user.setDOB(new Date());
            user.setFollowers(0);
            user.setFollowing(0);
            user.setDescription(String.format("Its empty here... %s has not set his/her description.", user.getUsername()));
            File f = Image.convertUrlToFile(getContext(), personPhoto.toString());
            user.setAvatar(f);


            try{
                if(db.isUsernameUnique(user.getUsername()) == Constants.ERROR){
                    google_sign_in_status = Constants.ERROR;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(google_sign_in_status == Constants.SUCCESS) db.insertObj(user);

            Handler handler = new Handler(Looper.getMainLooper());
            assert act != null;

            if(google_sign_in_status == Constants.SUCCESS) {
                ((MainActivity) act).main_dialog.startSuccessDialog("Successfully Registered User!");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)act).main_dialog.dismissDialog();
                        navToLogin();
                    }
                }, 2500);
            } else{
                ((MainActivity) act).main_dialog.startErrorDialog("Google duplicate usernames!");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity)act).main_dialog.dismissDialog();
                    }
                }, 5000);
            }


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SERVO", "signInResult:failed code=" + e.getStatusCode());
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