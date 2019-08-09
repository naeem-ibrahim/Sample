package com.algorepublic.brandmaker.ui.dashboard;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.algorepublic.brandmaker.BMApp;
import com.algorepublic.brandmaker.BaseActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.ui.answers.options.OptionsFragment;
import com.algorepublic.brandmaker.ui.home.HomeFragment;

import com.algorepublic.brandmaker.ui.login.LoginActivity;
import com.algorepublic.brandmaker.ui.splash.SplashActivity;
import com.algorepublic.brandmaker.ui.tabs.CategoryCheckoutTab;
import com.algorepublic.brandmaker.utils.Constants;
import com.algorepublic.brandmaker.utils.GPSListener;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    TextView toolbarTitle;
    TextView toolbarTime;
    TextView navHeaderTitle;
    TextView navHeaderEmail;
    private GPSListener gpsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView back = (ImageView) findViewById(R.id.btnBack);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTime = (TextView) findViewById(R.id.toolbar_time);
        navHeaderTitle = (TextView) findViewById(R.id.nav_header_title);
        navHeaderEmail = (TextView) findViewById(R.id.nav_header_email);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navHeaderTitle.setText(BMApp.db.getUserObj().getFirstName() + BMApp.db.getUserObj().getLastName());
        navHeaderEmail.setText(BMApp.db.getUserObj().getEmail());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(BMApp.db.getBoolean(Constants.CHECK_IN)) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            callFragmentAdd(R.id.container, CategoryCheckoutTab.getInstance(BMApp.db.getInt(Constants.CHECKED_IN_STORE_ID),BMApp.db.getString(Constants.CHECKED_IN_STORE_NAME)), null);
        }else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            callFragmentAdd(R.id.container, HomeFragment.getInstance(1), null);
        }
//        callFragmentAdd(R.id.container,  OptionsFragment.getInstance(false), null);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void reload(){
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    public void setListener(GPSListener listener){
        this.gpsListener = listener ;
    }

    public void setToolBar(String title, String time, boolean showTime) {
        toolbarTitle.setText(title);
        toolbarTime.setText(time);
        if (showTime) {
            toolbarTime.setVisibility(View.VISIBLE);
        } else {
            toolbarTime.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    public boolean onNavigationItemClick(View item) {
        // Handle navigation view item clicks here.
        int id = item.getId();

        if (id == R.id.nav_activity) {
            callFragmentAdd(R.id.container, HomeFragment.getInstance(0), null);
        } else if (id == R.id.nav_check_in) {
            callFragmentAdd(R.id.container, HomeFragment.getInstance(1), null);
        } else if (id == R.id.nav_notification) {
            callFragmentAdd(R.id.container, HomeFragment.getInstance(2), null);
        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.tv_logout) {
            BMApp.db.setUserObg(null);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.GPS_REQUEST) {
                if(gpsListener!=null) {
                    gpsListener.onEnable(true);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
