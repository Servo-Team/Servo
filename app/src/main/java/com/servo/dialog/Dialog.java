package com.servo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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

    public void startSuccessDialog(String text){
        startDialog(R.layout.success_alert,text);
    }

    public void startErrorDialog(String text){
        startDialog(R.layout.error_alert,text);
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
     * @text  either success text or error
     */
    private void startDialog(int layout_id, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);

        LayoutInflater inflator = activity.getLayoutInflater();
        View view = inflator.inflate(layout_id, null);
        if(layout_id == R.layout.error_alert) {
            ((TextView) view.findViewById(R.id.errorText)).setText(text);
        } else{
            ((TextView) view.findViewById(R.id.successText)).setText(text);
        }
        builder.setView(view);
        builder.setCancelable(true);

        dialog = builder.create();

        dialog.show();
    }

}
