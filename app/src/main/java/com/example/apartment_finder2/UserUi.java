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
import android.widget.LinearLayout;
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

    private TextView mName,mEmail,mF,mPhone,mCity,mPost,mR;
    DatabaseReference ref,ref2;
    User up;
    String sName,eName;
    Upload upl;
    LinearLayout mfav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ui);
        mName=findViewById(R.id.UserName);
        mEmail=findViewById(R.id.mail);
        mF=findViewById(R.id.noF);
        mPhone=findViewById(R.id.phone);
        mCity=findViewById(R.id.city);
        mPost=findViewById(R.id.nPost);
        mR=findViewById(R.id.nR);
        mfav=findViewById(R.id.favApa);

        ref=FirebaseDatabase.getInstance().getReference("Users");
        ref2=FirebaseDatabase.getInstance().getReference("uploads");
        up=new User();
        upl=new Upload();
        mfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserUi.this, Extendfavorites.class);
                startActivity(intent1);
            }
        });
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(User.class);
                    if(up.getEmail().equalsIgnoreCase(LoggedEmail))
                    {
                        sName=up.getName();
                        mName.setText(sName);
                        mEmail.setText(LoggedEmail);
                        mPhone.setText("");
                        mCity.setText("");

                        if(up.getmMobileNo()!=null)
                        {
                            mPhone.setText(up.getmMobileNo());
                        }
                        if(up.getmAddress()!=null)
                        {
                            mCity.setText(up.getmAddress());
                        }
                        if(ds.child("Favourites")!=null)
                        {
                            mF.setText(String.valueOf(ds.child("Favourites").getChildrenCount()));
                        }
                        if(ds.child("Reported")!=null)
                        {
                            mR.setText(String.valueOf(ds.child("Reported").getChildrenCount()));
                        }

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("Logged: ",LoggedEmail);
                int c=0;
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    upl=ds.getValue(Upload.class);
                    if(upl.getmLoggedEmail().equalsIgnoreCase(LoggedEmail))
                    {
                        ++c;
                    }
                }
                mPost.setText(String.valueOf(c));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
