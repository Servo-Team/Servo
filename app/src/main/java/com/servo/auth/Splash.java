package com.servo.auth;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

import java.util.Timer;

/**
 * <h1>Splash</h1>
 * <p>
 *  Controller for Servo's
 *  splash fragment
 * </p>
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 * @see     R.layout
 */
public class Splash extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        Activity act = getActivity();
        if(act instanceof MainActivity){
            ((MainActivity)act).animate_splash(view);
        }

        //After 5 seconds
        //move on to the login page
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navToLogin();
            }
        }, 5000);

        return view;
    }

    /**
     * Simply navigates to the
     * login page
     */
    private void navToLogin() {
        NavController ctrl = NavHostFragment.findNavController(this);
        ctrl.navigate(R.id.action_splash_to_login);
    }
}