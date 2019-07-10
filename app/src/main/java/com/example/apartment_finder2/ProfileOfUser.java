package com.example.apartment_finder2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileOfUser extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    String str1,str2,str3,str4;
    TextView memailTv,mnameTv;
    User up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_of_user);
        final String mEmail = getIntent().getStringExtra("Email");
        mUserDatabase = FirebaseDatabase.getInstance().getReference("Users");
        up = new User();
        memailTv=findViewById(R.id.emailTv);
        mnameTv=findViewById(R.id.nameTv);

        //Toast.makeText(ProfileOfUser.this, "You sent " + mEmail, Toast.LENGTH_LONG).show();

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(mEmail!=null)
                {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        up=ds.getValue(User.class);
                        if(up.getEmail().equalsIgnoreCase("kk42949671@gmail.com"))
                        {
                            //str1=mEmail;
                            str2=up.getName();
                            Toast.makeText(ProfileOfUser.this, "You sent " + str2, Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    if(str2!=null)
                    {
                        memailTv.setText(str2);
                    }
                }

                //memailTv.setText(str1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
