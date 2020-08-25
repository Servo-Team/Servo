package com.servo.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.servo.dialog.Dialog;
import com.servo.utils.Constants;

public class MainActivity extends AppCompatActivity {

    protected Dialog main_dialog;

    public void animate_splash(View view){
        Animation splash_bg_animate = AnimationUtils.loadAnimation(this,R.anim.splash_bg_anim);

        ConstraintLayout lyt = (ConstraintLayout) view.findViewById(R.id.mainLayoutSplash);

        lyt.startAnimation(splash_bg_animate);

    }

    //No back stack allowed in the authentication
    @Override
    public void onBackPressed() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        main_dialog = new Dialog(this);

    }
}