package com.example.apartment_finder2;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity  {
    public static  int checker=0;
    public static  String LoggedEmail="";
    public static  String LoggedName="";
    private Button mLoginOption;
    private Button mLogoutOption;
    private TextView mlogAndper;
    private Button make_advertise;
    private Button make_new;
    private DatabaseReference ref;
    String str,Phone;
    UploadValidationForm up;
    int ValidationFlag=0,CheckValidation=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginOption = (Button) findViewById(R.id.login_option);
        mLogoutOption = (Button) findViewById(R.id.logOut_option);
        mlogAndper = (TextView) findViewById(R.id.logAndper);
        make_advertise = (Button) findViewById(R.id.make_ad);

        ref=FirebaseDatabase.getInstance().getReference("ValidationForm");
        up=new UploadValidationForm();

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

        final String mEmail = getIntent().getStringExtra("Email");
        str=mEmail;
        //Toast.makeText(MainActivity.this, "You sent " + mEmail, Toast.LENGTH_LONG).show();

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
                    case R.id.profile:

                        if(checker==0)
                        {
                            AlertDialog alertDialog=builderAlertDialog("Sorry!","Your Must Log in First" ,true,MainActivity.this);
                            alertDialog.show();
                        }
                        /*else
                        {
                            Intent intent2 = new Intent(MainActivity.this, UserUi.class);
                            //intent2.putExtra("Email",mEmail);
                            //Toast.makeText(MainActivity.this, "You sent " + mEmail, Toast.LENGTH_LONG).show();
                            startActivity(intent2);
                        }*/

                        break;

                }
                return false;
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(UploadValidationForm.class);
                   if(up.getmES().equalsIgnoreCase(str))
                   {
                       if(up.getmValidation().equalsIgnoreCase("Validated"))
                       {
                           ValidationFlag=1;
                           CheckValidation=1;
                           Phone=up.getmPhone();
                       }
                       else if(up.getmValidation().equalsIgnoreCase("Not_Validate_Yet"))
                       {
                           ValidationFlag=1;
                           CheckValidation=0;
                       }
                       break;
                   }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    }
    public void openActivity4() {

        if(checker==0)
        {
            CheckLogin();
        }
        else if(checker==1&&ValidationFlag==0)
        {
            Intent intent1 = new Intent(this, ValidationForm.class);
            intent1.putExtra("Email",str);
            //Toast.makeText(MainActivity.this, "You sent " + str, Toast.LENGTH_LONG).show();
            startActivity(intent1);
        }
        else if(checker==1&&ValidationFlag==1&&CheckValidation==0)
        {
            /*Intent intent1 = new Intent(this, NotValidYet.class);
            intent1.putExtra("Email",str);
            //Toast.makeText(MainActivity.this, "You sent " + str, Toast.LENGTH_LONG).show();
            startActivity(intent1);*/
            AlertDialog alertDialog=builderAlertDialog("Sorry!","Your Validation Form is not Validated Yet\nYou will be N" +
                    "otified via Email",true,MainActivity.this);
            alertDialog.show();
        }
        else if(checker==1&&ValidationFlag==1&&CheckValidation==1)
        {
            Intent intent1 = new Intent(this, Advertisement.class);
            intent1.putExtra("Email",str);
            intent1.putExtra("Phone",Phone);
            //ValidationFlag=0;
            //CheckValidation=0;
            //intent1.putExtra("Mobile",);
            //Toast.makeText(MainActivity.this, "You sent " + str, Toast.LENGTH_LONG).show();
            startActivity(intent1);
        }




        //Intent intent = new Intent(this, Advertisement.class);
        //startActivity(intent);
    }
    public void CheckLogin()
    {
        if(checker==0)
        {
            Intent intent=new Intent(this,AdLogIn.class);
            startActivity(intent);
            return;
        }
    }
    public static AlertDialog builderAlertDialog (String title, String message, boolean isCancelable, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setTitle(title);

        if (isCancelable){
            builder.setPositiveButton(android.R.string.ok,null);
        }else {
            builder.setCancelable(false);
        }
        return builder.create();
    }



}
