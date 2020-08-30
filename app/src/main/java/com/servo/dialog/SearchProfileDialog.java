package com.servo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.servo.auth.R;

public class SearchProfileDialog {

    private Activity act;
    private Dialog dialog;
    private View globalView;
    private String username;

    public SearchProfileDialog(Activity act, String username){
        this.act = act; this.username = username;
    }

    public void startDialog(){
        dialog = new Dialog(act, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflator = act.getLayoutInflater();
        globalView = inflator.inflate(R.layout.search_user_profile, null);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(globalView);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
