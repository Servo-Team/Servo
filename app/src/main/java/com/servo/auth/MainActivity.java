package com.servo.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    public void animate_splash(View view){
        Animation text_animate = AnimationUtils.loadAnimation(this,R.anim.splash_bg_anim);
        Animation right = AnimationUtils.loadAnimation(this, R.anim.right_splash_but);
        Animation left = AnimationUtils.loadAnimation(this, R.anim.left_splash_but);
        view.findViewById(R.id.splashHeader).setAnimation(text_animate);
        view.findViewById(R.id.logoSplash).setAnimation(text_animate);
        view.findViewById(R.id.leftButton).setAnimation(left);
        view.findViewById(R.id.rightButton).setAnimation(right);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
}