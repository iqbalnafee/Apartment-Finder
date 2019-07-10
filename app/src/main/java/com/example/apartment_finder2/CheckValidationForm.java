package com.example.apartment_finder2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckValidationForm extends AppCompatActivity {

    private TextView mFiName,mLaName,mACVage,mACVsex,mACVocc,mACVnid,mACVchoice;
    String fn,ln,ag,se,occ,nid,cho,clientMail,mkey;
    private Button mvalidButton,mInvalidButton;
    private DatabaseReference mUserDatabase;
    UploadValidationForm up;
    UploadValidationForm copyUp;
    //List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_validation_form);
        mFiName=findViewById(R.id.FiName);
        mLaName=findViewById(R.id.LaName);
        mACVage=findViewById(R.id.ACVage);
        mACVsex=findViewById(R.id.ACVsex);
        mACVocc=findViewById(R.id.ACVocc);
        mACVnid=findViewById(R.id.ACVnid);
        mACVchoice=findViewById(R.id.ACVchoice);
        mvalidButton=findViewById(R.id.validButton);
        mInvalidButton=findViewById(R.id.InvalidButton);
        final String sEmail=getIntent().getStringExtra("Email");
        mUserDatabase = FirebaseDatabase.getInstance().getReference("ValidationForm");
        up=new UploadValidationForm();
        copyUp=new UploadValidationForm();
        //final List<String> list = new ArrayList<String>();
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(UploadValidationForm.class);
                    if(up.getmES().equalsIgnoreCase(sEmail))
                    {
                        mFiName.setText(up.getmFname());
                        mLaName.setText(up.getmLname());
                        mACVage.setText(up.getmAge());
                        mACVsex.setText(up.getmSex());
                        mACVocc.setText(up.getmOcc());
                        mACVnid.setText(up.getmNid());
                        mACVchoice.setText(up.getmPchoice());
                        fn=up.getmFname();
                        ln=up.getmLname();
                        ag=up.getmAge();
                        se=up.getmSex();
                        occ=up.getmOcc();
                        nid=up.getmNid();
                        cho=up.getmPchoice();
                        clientMail=sEmail;
                        copyUp=up;
                        mkey=up.getmKey();
                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Validated(View v) {
        mUserDatabase.child(mkey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("mValidation").setValue("Validated");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Intent intent=new Intent(this,SendValidityEmail.class);
        startActivity(intent);
    }
    public void  InValidated(View v) {

    }



}
