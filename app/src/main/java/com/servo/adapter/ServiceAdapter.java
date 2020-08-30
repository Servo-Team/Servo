package com.servo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.servo.auth.R;
import com.servo.database.Service;
import com.servo.database.ServiceDatabase;
import com.servo.utils.Constants;
import com.servo.view.ServiceHolder;
import com.servo.view.ServiceModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceHolder> {

    Context ctx;
    Activity act;
    List<ServiceModel> servicesAdded;

    public ServiceAdapter(Context ctx, List<ServiceModel> servicesAdded, Activity act) {
        this.ctx = ctx;
        this.servicesAdded = servicesAdded;
        this.act = act;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContextThemeWrapper ctxwrapper = new ContextThemeWrapper(ctx, R.style.Theme_MaterialComponents);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.row_services, null);

        return new ServiceHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, final int position) {
        holder.hTitle.setText(servicesAdded.get(position).getTitle());
        holder.hDesc.setText(servicesAdded.get(position).getDesc());


        if(servicesAdded.get(position).isFinished()){
            holder.toggle.toggle();
            holder.anim.setAnimation(R.raw.done_blue);
        }

        final ServiceHolder fHolder = holder;
        holder.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ServiceDatabase db = new ServiceDatabase();
                    if (fHolder.toggle.isChecked()) {
                        db.setFinishedOrUnfinished(servicesAdded.get(position).getServiceID(), Constants.SERVICE_FINISHED);
                        Toast.makeText(act, "FINISHED!", Toast.LENGTH_LONG).show();
                    } else {
                        db.setFinishedOrUnfinished(servicesAdded.get(position).getServiceID(), Constants.SERVICE_PENDING);
                        Toast.makeText(act, "UNFINISHED!", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.err.println("ERROR ONBINDVIEWHOLDER SERVICE ADAPTER");
                }
            }
        });

        for(int i=0; i<servicesAdded.get(position).getChipText().length; i++){
            Chip chip = (Chip) act.getLayoutInflater().inflate(R.layout.servicechip, null, false);
            chip.setText(servicesAdded.get(position).getChipText()[i]);
            holder.chipG.addView(chip);
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.servicesAdded.size();
    }
}
