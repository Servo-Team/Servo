package com.servo.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.servo.auth.MainActivity;
import com.servo.auth.R;
import com.servo.database.User;

import org.w3c.dom.Text;


public class HomeFragment extends Fragment {
    private View globalView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalView = inflater.inflate(R.layout.fragment_home, container, false);

        Activity act = getActivity();
        User user = ((HomeActivity)act).USER;
        assert act!=null;
        TextView userText = (TextView) globalView.findViewById(R.id.welcomeUsername);

        userText.setText(user.getUsername());

        if(user.getFollowing() == 0){
            globalView.findViewById(R.id.noFollowingText).setAlpha(1);
        }

        return globalView;
    }
}