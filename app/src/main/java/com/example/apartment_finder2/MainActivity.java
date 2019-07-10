package com.example.apartment_finder2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  {
    public static  int checker=0;
    private Button mLoginOption;
    private Button mLogoutOption;
    private TextView mlogAndper;
    private Button make_advertise;
    private Button make_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginOption = (Button) findViewById(R.id.login_option);
        mLogoutOption = (Button) findViewById(R.id.logOut_option);
        mlogAndper = (TextView) findViewById(R.id.logAndper);
        make_advertise = (Button) findViewById(R.id.make_ad);

        mLoginOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
        mLogoutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        make_advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        final SearchFragment searchFragment = new SearchFragment();
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        if(checker==1)
        {
            mLogoutOption.setVisibility(View.VISIBLE);
            mLoginOption.setVisibility(View.INVISIBLE);
        }
        else
        {
            mLogoutOption.setVisibility(View.INVISIBLE);
        }

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:

                        Intent intent1 = new Intent(MainActivity.this, Search.class);
                        startActivity(intent1);

                        break;


                }


                return false;
            }
        });


    }









    private void setFragment(Fragment fragment)
    {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

    public void openActivity2() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void openActivity3() {
        FirebaseAuth.getInstance().signOut();
        finish();
        checker=0;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        /*mLoginOption.setVisibility(View.VISIBLE);
        mlogAndper.setVisibility(View.VISIBLE);
        mLogoutOption.setVisibility(View.INVISIBLE);*/
    }
    public void openActivity4() {
        Intent intent = new Intent(this, Advertisement.class);
        startActivity(intent);
    }



}
