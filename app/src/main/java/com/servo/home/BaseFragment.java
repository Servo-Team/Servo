package com.servo.home;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity fragAct = requireActivity();
        androidx.appcompat.app.ActionBar actBar = ((AppCompatActivity)fragAct).getSupportActionBar();
        actBar.show();
    }
}
