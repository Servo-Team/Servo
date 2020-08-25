package com.servo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;

import com.servo.auth.R;

/**
 * <h1>Dialog</h1>
 * <p>
 *  Used as a base
 *  unique dialog shared between
 *  all the activities.
 * </p>
 *
 *
 * @author  Amanuel Bogale
 * @version 0.1
 * @since   2020-08-24
 */
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

    public void dismissDialog() throws IllegalStateException{
        if(this.dialog != null) {
            this.dialog.dismiss();
        } else{
            throw new IllegalStateException("DIALOG HAS NOT BEEN INSTATATED");
        }
    }

    /**
     * Starts a dialog which
     * inside contains that layout.
     * @param layout_id ID of the layout specified
     */
    private void startDialog(int layout_id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);

        LayoutInflater inflator = activity.getLayoutInflater();
        builder.setView(inflator.inflate(layout_id, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

}
