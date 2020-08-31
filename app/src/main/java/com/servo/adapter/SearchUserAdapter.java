package com.servo.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.servo.auth.R;
import com.servo.database.Service;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.dialog.SearchDialog;
import com.servo.home.HomeActivity;
import com.servo.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SearchUserAdapter extends BaseAdapter {
    Activity act;


    List<String> titles;
    List<String> descs;
    List<File> images;

    public SearchUserAdapter(Activity act){
        this.act = act;
        titles = new ArrayList();
        descs  = new ArrayList();
        images = new ArrayList();
        try {
            UserDatabase db = new UserDatabase();
            List<Object> users = db.getObjsWithAvatar(act);

            for(int i=0; i<users.size(); i++){
                User user = (User) users.get(i);
                if(((HomeActivity)act).USER.getUsername().equals(user.getUsername())) continue;
                titles.add(user.getUsername());
                descs.add(user.getDescription());
                images.add(user.getAvatar());
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int i) {
        return titles.get(i); //return username AKA ID
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View customView = act.getLayoutInflater().inflate(R.layout.row_search, null);

        UserDatabase db = new UserDatabase();

        File f = null;
        try {
            f = db.getAvatar(titles.get(i), act);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView image = customView.findViewById(R.id.searchListImage);
        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
        image.setImageBitmap(bitmap);


        TextView text = customView.findViewById(R.id.searchListTitle);
        text.setText(titles.get(i));

        TextView desc = customView.findViewById(R.id.searchListDesc);
        desc.setText(descs.get(i));

        return customView;
    }
}
