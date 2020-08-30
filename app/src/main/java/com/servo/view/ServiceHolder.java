package com.servo.view;

import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.chip.ChipGroup;
import com.servo.auth.R;

public class ServiceHolder extends RecyclerView.ViewHolder {
    public TextView hTitle, hDesc;
    public ChipGroup chipG;
    public ToggleButton toggle;
    public LottieAnimationView anim;

    public ServiceHolder(@NonNull View itemView) {
        super(itemView);
        this.hTitle = itemView.findViewById(R.id.serviceTitle);
        this.hDesc  = itemView.findViewById(R.id.serviceDesc);
        this.chipG  = itemView.findViewById(R.id.chipGroupServices);
        this.toggle = itemView.findViewById(R.id.toggleButton);
        this.anim   = itemView.findViewById(R.id.pendingAnimation);
    }
}
