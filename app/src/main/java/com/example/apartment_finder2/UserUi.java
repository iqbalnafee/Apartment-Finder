package com.example.apartment_finder2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.Set;

import static com.example.apartment_finder2.MainActivity.LoggedEmail;

public class UserUi extends AppCompatActivity {

    private TextView mName,mEmail;
    DatabaseReference ref;
    User up;
    String sName,eName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ui);
        mName=findViewById(R.id.UserUiName);
        mEmail=findViewById(R.id.UserUiEmail);
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        Menu menu = navigationView.getMenu();
        /*MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);*/
        ref=FirebaseDatabase.getInstance().getReference("Users");
        up=new User();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(User.class);
                    if(up.getEmail().equalsIgnoreCase(LoggedEmail))
                    {
                        Log.d("Name: ","sName "+up.getEmail());
                        sName=up.getName();
                        eName=up.getEmail();
                        //mName.setText(sName);
                        mEmail.setText(LoggedEmail);
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
