package com.servo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.servo.auth.R;
import com.servo.utils.Constants;
import com.servo.utils.Image;
import com.squareup.picasso.Picasso;

public class ProfileAdapter extends ArrayAdapter<String> {
    Context ctx;
    String titles[];
    int images[];

    public ProfileAdapter(@NonNull Context context, String[] titles, int images[]) {
        super(context, R.layout.list_item, R.id.title, titles);
        this.images = images;
        this.titles = titles;
        this.ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.list_item, parent, false);
        TextView text = (TextView) row.findViewById(R.id.title);
        ImageView image = (ImageView) row.findViewById(R.id.imageListView);

        text.setText(titles[position]);
        Picasso.with(ctx)
                .load(Constants.image_urls_profile[position])
                .into(image);
        return row;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
}
