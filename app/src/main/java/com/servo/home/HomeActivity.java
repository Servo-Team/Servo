package com.servo.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.servo.adapter.ProfileAdapter;
import com.servo.adapter.ViewPagerAdapter;
import com.servo.auth.R;
import com.servo.database.User;
import com.servo.database.UserDatabase;
import com.servo.dialog.Dialog;
import com.servo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.servo.utils.Permission.verifyStoragePermissions;

public class HomeActivity extends AppCompatActivity {

    public Dialog main_dialog;
    public User USER = new User();
    public int USER_ID;
    protected MenuItem currMenu;

    private AppBarConfiguration.Builder appbar;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        getSupportActionBar().hide();
        verifyStoragePermissions(this);
        main_dialog = new Dialog(this);

        Intent data = getIntent();
        Bundle bundle = data.getExtras();
        UserDatabase db = new UserDatabase();
        try {
            USER_ID = bundle.getInt("USER_ID");
            USER = (User) db.getObj(USER_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.homeFragment:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.searchFragment:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.addFragment:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.messageFragment:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.profileFragment:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return false; //not found
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if(currMenu!=null) currMenu.setChecked(false);
                else bottomNav.getMenu().getItem(0).setChecked(false);

                bottomNav.getMenu().getItem(position).setChecked(true);
                currMenu = bottomNav.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        setUpViewPager(viewPager);

    }

    public void setUpViewPager(ViewPager viewPagerParam){

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                1
        );

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new SearchFragment());
        adapter.addFragment(new AddFragment());
        adapter.addFragment(new MessageFragment());
        adapter.addFragment(new ProfileFragment());

        viewPagerParam.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        viewPager.setCurrentItem(0);
    }
}