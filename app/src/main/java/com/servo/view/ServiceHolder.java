package com.servo.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.servo.auth.R;

public class ServiceHolder extends RecyclerView.ViewHolder {
    public TextView hTitle, hDesc;
    public ChipGroup chipG;

    public ServiceHolder(@NonNull View itemView) {
        super(itemView);
        this.hTitle = itemView.findViewById(R.id.serviceTitle);
        this.hDesc  = itemView.findViewById(R.id.serviceDesc);
        this.chipG  = itemView.findViewById(R.id.chipGroupServices);
    }
}
