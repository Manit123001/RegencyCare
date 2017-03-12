package com.example.mcnewz.regencycare.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;
import android.widget.Toast;


import com.example.mcnewz.regencycare.R;
import com.example.mcnewz.regencycare.dao.ItemDao;
import com.example.mcnewz.regencycare.fragment.AlertFragment;
import com.example.mcnewz.regencycare.fragment.MapFragment;
import com.example.mcnewz.regencycare.fragment.OneFragment;
import com.example.mcnewz.regencycare.fragment.RegencyTabShowFunction;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlertFragment.FragmentListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // AlertTab Fragment here !
        if (savedInstanceState== null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,
                            RegencyTabShowFunction.newInstance(),
                            "RegencyTabShowFunction")
                    .commit();
        }

        String token = FirebaseInstanceId.getInstance().getToken();
        //Log.d("Token Me", token);

        initTnstances();

    }

    private void initTnstances() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // drawer layout Here
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onPhotoItemClicked(ItemDao dao) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("dao",  dao);
        startActivity(intent);
    }
}
