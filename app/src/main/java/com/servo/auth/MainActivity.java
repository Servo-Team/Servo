package com.servo.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.servo.dialog.Dialog;
import com.servo.home.HomeActivity;


public class MainActivity extends AppCompatActivity {

    protected Dialog main_dialog;

    public void animate_splash(View view){
        Animation splash_bg_animate = AnimationUtils.loadAnimation(this,R.anim.splash_bg_anim);

        ConstraintLayout lyt = (ConstraintLayout) view.findViewById(R.id.mainLayoutSplash);

        lyt.startAnimation(splash_bg_animate);

    }

    public void navToHomePage(Bundle bundle){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtras(bundle);

        startActivity(intent);
        finish();
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
//        AppEventsLogger.activateApp(getApplication());
    }
}