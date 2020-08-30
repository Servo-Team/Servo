package com.servo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.servo.auth.R;
import com.servo.database.ServiceDatabase;
import com.servo.home.HomeActivity;
import com.servo.utils.StringManupilation;


public class SearchDialog {

    private Activity activity;
    private AlertDialog dialog;

    public SearchDialog(Activity activity){
        this.activity = activity;
    }


    /**
     * Starts a dialog which
     * inside contains that layout.
     * @param service list string
     * @text  either success text or error
     */
    public void startDialog(String service){
        final String fservice = service;
        dialog = new AlertDialog.Builder(this.activity)
                            .setTitle("Are you sure?")
                            .setMessage("Do you want to start working on this task?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ServiceDatabase db = new ServiceDatabase();
                                    try {
                                        db.updateObj(StringManupilation.getIDFromList(fservice), ((HomeActivity)activity).USER_ID );
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(activity, "Updated!", Toast.LENGTH_LONG).show();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(activity, "Canceled!", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();

    }

    private void dismiss(){dialog.dismiss();}


}
