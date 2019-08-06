package com.algorepublic.brandmaker.ui.dashboard;

import android.os.Bundle;

import com.algorepublic.brandmaker.BaseActivity;
import com.algorepublic.brandmaker.R;
import com.algorepublic.brandmaker.ui.home.HomeFragment;

import com.google.android.material.navigation.NavigationView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView back = (ImageView) findViewById(R.id.btnBack);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTime = (TextView) findViewById(R.id.toolbar_time);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        callFragmentAdd(R.id.container, HomeFragment.getInstance(1), null);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
