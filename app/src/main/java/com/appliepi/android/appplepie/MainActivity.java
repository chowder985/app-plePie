package com.appliepi.android.appplepie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static MainActivity sMainActivity;
    private String token;

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    TextView text1;
    private int isAdmin;
    private String result;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sMainActivity = this;

        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d("Token from deter", token);
        new GetUserInfoPost(token).execute();
    }

    public void setNavigationView(String navHeaderString, int navHeaderType, int id){
        View header = navigationView.getHeaderView(0);
        text1 = (TextView) header.findViewById(R.id.text1);
        text1.setText(navHeaderString);
        isAdmin = navHeaderType;
        this.id = id;
    }

    public void runGetUserInfo(){
        new GetUserInfoPost(token).execute();
    }

    public void Data(String result) {
        Log.d("result3", result);
        this.result = result;
    }

    public void myData(String result) {
        Log.d("myData", result);
        this.result = result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_intro) {
            // Handle the camera action
            MainFragment fragment = new MainFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        } else if (id == R.id.nav_history) {

            HistoryFragment fragment = new HistoryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        } else if (id == R.id.nav_performance) {

            PerformanceFragment fragment = new PerformanceFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        } else if (id == R.id.nav_benefit) {

            BenefitFragment fragment = new BenefitFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        } else if (id == R.id.nav_application) {

            ApplicationFragment fragment = new ApplicationFragment();
            if(token!=null){
                Bundle args = new Bundle();
                args.putString("token", token);
                fragment.setArguments(args);
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

        } else if (id == R.id.nav_see) {
            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("로그인 중...");
            progressDialog.show();

            Log.d("MainActivity isAdmin", String.valueOf(isAdmin));
            if (isAdmin == 1)
                new GetForms(token).execute();
            else
                new GetMyForms(token, this.id).execute();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            SeeFragment fragment;
                            fragment = new SeeFragment();
                            Bundle args = new Bundle();
                            args.putInt("isAdmin", isAdmin);
                            if(token!=null) {
                                args.putString("token", token);
                            }
                            //Log.d("result4", result);
                            args.putString("result", result);
                            fragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                                    .beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

                            progressDialog.dismiss();
                        }
                    }, 2000);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static MainActivity getInstance(){
        return sMainActivity;
    }
}
