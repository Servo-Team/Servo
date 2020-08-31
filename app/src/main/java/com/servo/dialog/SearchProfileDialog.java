package com.servo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.servo.auth.MainActivity;
import com.servo.auth.R;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.home.HomeActivity;
import com.servo.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;

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

        //User
        try {
            UserDatabase db = new UserDatabase();
            Object obj = db.getObjViaUsername(act, username);
            User user = (User) obj;
            ((TextView) globalView.findViewById(R.id.searchUserProfileUsername)).setText(user.getUsername());
            ((TextView) globalView.findViewById(R.id.searchUserProfileDesc)).setText(user.getDescription());
            ((TextView) globalView.findViewById(R.id.searchUserProfileFollowersNumber)).setText(Integer.toString(user.getFollowers()));
            ((TextView) globalView.findViewById(R.id.searchUserProfileFollowingNumber)).setText(Integer.toString(user.getFollowing()));


            File f = null;
            try {
                f = db.getAvatar(user.getUsername(), act);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ImageView image = (ImageView) globalView.findViewById(R.id.searchUserProfileAvatar);
            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            image.setImageBitmap(bitmap);

            ToggleButton button = (ToggleButton)globalView.findViewById(R.id.searchUserProfileButton);

            //Toggle if followed
            if(db.isFollowed( ((HomeActivity)act).USER.getUsername(), user.getUsername())){
                button.toggle();
            }


            final ToggleButton fButton = button;
            final User fUser = user;
            //On Click toggle
            button.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {
                    try {
                        UserDatabase db = new UserDatabase();
                        if (fButton.isChecked()) {
                            db.addFollower(((HomeActivity) act).USER.getUsername(), fUser.getUsername());
                            Toast.makeText(act,"FOLLOWED!", Toast.LENGTH_SHORT).show();
                        } else{
                            db.removeFollower(((HomeActivity) act).USER.getUsername(), fUser.getUsername());
                            Toast.makeText(act,"UNFOLLOWED!", Toast.LENGTH_SHORT).show();
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

        } catch(Exception e){
            e.printStackTrace();
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(globalView);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
