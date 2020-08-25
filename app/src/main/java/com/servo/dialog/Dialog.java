package com.servo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;

import com.servo.auth.R;

public class Dialog {

    private Activity activity;
    private AlertDialog dialog;

    public Dialog(Activity activity){
        this.activity = activity;
    }

    public void startSuccessDialog(){
        startDialog(R.layout.success_alert);
    }

    public void startErrorDialog(){
        startDialog(R.layout.error_alert);
    }


    public void dismissDialog(){
        if(this.dialog != null) {
            this.dialog.dismiss();
        } else{
            throw new IllegalStateException("DIALOG HAS NOT BEEN INSTATATED");
        }
    }

    private void startDialog(int layout_id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);

        LayoutInflater inflator = activity.getLayoutInflater();
        builder.setView(inflator.inflate(layout_id, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

}
