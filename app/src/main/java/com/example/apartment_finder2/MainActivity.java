package com.example.apartment_finder2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  {
    DrawerLayout mdrawerLayout;
    CardView mSearch;
    Toolbar mtoolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView mBlogs;
    public static  int checker=0;
    public static  String LoggedEmail="";
    public static  String LoggedName="";
    private CardView mLoginOption;
    private CardView mloanCalc;
    private CardView mProfile;
    private CardView mFaques,mfeed,mChtBot;
    private Button mLogoutOption;
    private TextView mlogAndper;
    private CardView make_advertise;
    private Button make_new;
    private DatabaseReference ref;
    String str,Phone;
    UploadValidationForm up;
    int ValidationFlag=0,CheckValidation=0;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SetupToolbar();
        mLoginOption =  findViewById(R.id.login_option);
        //mLogoutOption = (Button) findViewById(R.id.logOut_option);
        //mlogAndper = (TextView) findViewById(R.id.logAndper);
        make_advertise = findViewById(R.id.make_ad);
        mloanCalc=findViewById(R.id.loancalc);
        mProfile=findViewById(R.id.Uprofile);
        mFaques=findViewById(R.id.faques);
        mSearch=findViewById(R.id.Cardsearch);
        mfeed=findViewById(R.id.FeedbackMessage2);
        img=findViewById(R.id.InOrOut);
        mChtBot=findViewById(R.id.chtBot);

        ref=FirebaseDatabase.getInstance().getReference("ValidationForm");
        up=new UploadValidationForm();

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Search.class);
                startActivity(intent1);
            }
        });

        mChtBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, ChatBot.class);
                startActivity(intent1);
            }
        });

        mfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker==0)
                {
                    AlertDialog alertDialog=builderAlertDialog("Sorry!","Your must log in first" ,true,MainActivity.this);
                    alertDialog.show();
                    return;
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, FeedbackForm.class);
                    startActivity(intent);
                }
            }
        });

        mloanCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, loanCalculation.class);
                startActivity(intent1);
            }
        });

        mFaques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewSubmission.class);
                intent.putExtra("DrawerType","faq");
                startActivity(intent);
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checker==0)
                {
                    AlertDialog alertDialog=builderAlertDialog("Sorry!","Your must log in first" ,true,MainActivity.this);
                    alertDialog.show();
                    return;
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, UserUi.class);
                    startActivity(intent);
                }
            }
        });
        /*mLogoutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });*/

        mLoginOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(checker==0)
               {
                   openActivity2();
               }
               else
               {
                   openActivity3();
               }

            }
        });

        make_advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        });
        final String mEmail = getIntent().getStringExtra("Email");
        str=mEmail;
        //Toast.makeText(MainActivity.this, "You sent " + mEmail, Toast.LENGTH_LONG).show();

        if(checker==1)
        {
            //Bitmap bMap = BitmapFactory.decodeFile("logout2.jpg");
            //img.setImageBitmap(bMap);
            //mLogoutOption.setVisibility(View.VISIBLE);
            //mLoginOption.set
            img.setImageResource(R.drawable.logout2);
        }
        else
        {
            img.setImageResource(R.drawable.log_in);
            //mLogoutOption.setVisibility(View.INVISIBLE);
        }

        /*navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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


                        break;

                }
                return false;
            }
        });*/

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
            AlertDialog alertDialog=builderAlertDialog("Sorry!","Your validation form is not validated Yet\nYou will be n" +
                    "otified via email",true,MainActivity.this);
            alertDialog.show();
        }
        else if(checker==1&&ValidationFlag==1&&CheckValidation==1)
        {
            Intent intent1 = new Intent(this, Advertisement.class);
            intent1.putExtra("Email",str);
            intent1.putExtra("Phone",Phone);
            startActivity(intent1);
        }
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

    /*private void SetupToolbar()
    {
        mdrawerLayout=findViewById(R.id.drawerLayout);
        mtoolbar=findViewById(R.id.toolbar);
        mBlogs=findViewById(R.id.Iblog);
        setSupportActionBar(mtoolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,mdrawerLayout,mtoolbar,R.string.app_name,R.string.app_name);
        mdrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }*/
    public void BlogClick (View v)
    {
        Intent intent = new Intent(MainActivity.this, NewSubmission.class);
        intent.putExtra("DrawerType","Blog");
        startActivity(intent);
    }
    public void feedbackClick (View v)
    {
        if(checker==0)
        {
            AlertDialog alertDialog=builderAlertDialog("Sorry!","Your Must Log in First" ,true,MainActivity.this);
            alertDialog.show();
            return;
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, FeedbackForm.class);
            startActivity(intent);
        }
    }
    public void faqClick (View v)
    {
        Intent intent = new Intent(MainActivity.this, NewSubmission.class);
        intent.putExtra("DrawerType","faq");
        startActivity(intent);
    }
    public void formSub(View v)
    {
        Intent intent = new Intent(MainActivity.this, icchamoto.class);
        //intent.putExtra("DrawerType","faq");
        startActivity(intent);
    }
    public void See(View v)
    {
        Intent intent = new Intent(MainActivity.this, icchamoto.class);
        //intent.putExtra("DrawerType","faq");
        startActivity(intent);
    }




}
