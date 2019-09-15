package com.example.apartment_finder2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckValidationForm extends AppCompatActivity {

    private TextView mFiName,mLaName,mACVage,mACVsex,mACVocc,mACVnid,mACVchoice,mPAdd,mMobileNum,memail_show;
    String fn,ln,ag,se,occ,nid,cho,clientMail,mkey;

    private DatabaseReference mUserDatabase;
    private RecyclerView mResultList;
    UploadValidationForm up;
    UploadValidationForm copyUp;
    String sss;
    String str1,str2;
    public int CountImageURL=0;
    //List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_validation_form);
        memail_show=findViewById(R.id.email_show);
        mFiName=findViewById(R.id.FiName);
        mLaName=findViewById(R.id.LaName);
        mACVage=findViewById(R.id.ACVage);
        mACVsex=findViewById(R.id.ACVsex);
        mACVocc=findViewById(R.id.ACVocc);
        mACVnid=findViewById(R.id.ACVnid);
        mACVchoice=findViewById(R.id.ACVchoice);

        //mPAdd=findViewById(R.id.PAdd);
        mMobileNum=findViewById(R.id.MobileNum);

        mResultList = (RecyclerView) findViewById(R.id.result_list_Check);
        mResultList.setHasFixedSize(true);
        final String sEmail=getIntent().getStringExtra("Email");
        sss=sEmail;
        mUserDatabase = FirebaseDatabase.getInstance().getReference("ValidationForm");
        up=new UploadValidationForm();
        copyUp=new UploadValidationForm();

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(UploadValidationForm.class);
                    if(up.getmES().equalsIgnoreCase(sEmail))
                    {
                        memail_show.setText(up.getmES());
                        mFiName.setText(up.getmFname());
                        mLaName.setText(up.getmLname());
                        mACVage.setText(up.getmAge());
                        mACVsex.setText(up.getmSex());
                        mACVocc.setText(up.getmOcc());
                        mACVnid.setText(up.getmNid());
                        mACVchoice.setText(up.getmSeeMblNum());
                        mMobileNum.setText(up.getmPhone());
                        str1=up.getmImageUrl1();
                        str2=up.getmImageUrl2();
                        fn=up.getmFname();
                        ln=up.getmLname();
                        ag=up.getmAge();
                        se=up.getmSex();
                        occ=up.getmOcc();
                        nid=up.getmNid();
                        cho=up.getmPhone();
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
        mResultList = findViewById(R.id.result_list_Check);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        Query firebaseSearchQuery = mUserDatabase.orderByChild("mImageUrl1").startAt("").endAt(""+ "\uf8ff");
        FirebaseRecyclerAdapter<UploadValidationForm, CheckValidationForm.UploadViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UploadValidationForm, CheckValidationForm.UploadViewHolder>(

                UploadValidationForm.class,
                R.layout.check_valiadation_image_layout,
                CheckValidationForm.UploadViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(UploadViewHolder viewHolder, UploadValidationForm model, int position) {
                if(CountImageURL==0)
                {
                    viewHolder.setDetails(getApplicationContext(), str1,str2);
                }
                ++CountImageURL;
            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }
    public static class UploadViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public UploadViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Context ctx, String str1, String str2){

            ImageView imageView1=mView.findViewById(R.id.ImageNIDFront);
            ImageView imageView2=mView.findViewById(R.id.ImageNIDBack);

            Picasso.with(ctx).load(str1).resize(150,150).into(imageView1);
            Picasso.with(ctx).load(str2).resize(150,150).into(imageView2);
        }

    }
    public void ClickValidate(View v)
    {
        mUserDatabase.child(mkey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("mValidation").setValue("Validated");
                Toast.makeText(CheckValidationForm.this,"Validated the form",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Intent intent=new Intent(this,SendValidityEmail.class);
        intent.putExtra("RecipientMail",sss);
        startActivity(intent);
    }
    public void ClickInValidate(View v)
    {
        mUserDatabase.child(mkey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //dataSnapshot.getRef().removeValue();
                dataSnapshot.getRef().child("mValidation").setValue("Invalidated");
                Toast.makeText(CheckValidationForm.this,"Invalidated the  form",Toast.LENGTH_LONG).show();
                //Intent intent=new Intent(CheckValidationForm.this,ValidationForm.class);
                //startActivity(intent);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Intent intent=new Intent(this,SendValidityEmail.class);
        intent.putExtra("RecipientMail",sss);
        startActivity(intent);
    }


    public void HomeButton(View v)
    {
        Intent intent=new Intent(this,AdminUi.class);
        startActivity(intent);
    }




}
