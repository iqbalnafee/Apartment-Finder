package com.example.apartment_finder2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.apartment_finder2.ChatAdapter.MessageChatAdapter;
import com.example.apartment_finder2.ChatHelper.ExtraIntent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.Empty;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.apartment_finder2.MainActivity.LoggedEmail;
import static com.example.apartment_finder2.MainActivity.checker;

public class ExtendSearch extends AppCompatActivity {

    private TextView rent,price,bedrooms,location,gas,lift,secu,school,hos,bus,mEmailShow;

    private DatabaseReference mUserDatabase;
    private DatabaseReference ref,mChatting;
    private FirebaseDatabase mdataBase;
    private RecyclerView mResultList;
    public int CountImageURL=0;
    private static final int REQUEST_CALL = 1;
    private CheckBox c1,c2,c3,c4,c5;
    private Button breport;
    private ImageView mWish;
    ArrayList<String> mSReports;

    ImageView imageView1,imageView2,imageView3,imageView4;
    String str1,str2,str3,str4,str5,ReciepientPhone,choiceOnPhone,SendLocation;
    long CreatedATEmail;
    Upload up;
    UploadValidationForm vf;
    User us;
    int level;
    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_search);

        imageView1=findViewById(R.id.i1);
        imageView2=findViewById(R.id.i2);
        imageView3=findViewById(R.id.i3);
        imageView4=findViewById(R.id.i4);

        breport=findViewById(R.id.ReportSubmitted);
        viewFlipper = findViewById(R.id.vFlipper);
        //viewFlipper.startFlipping();

        c1=findViewById(R.id.VS);
        c2=findViewById(R.id.InAp);
        c3=findViewById(R.id.Abusive);
        c4=findViewById(R.id.FP);
        c5=findViewById(R.id.Misbehave);

        breport.setVisibility(View.INVISIBLE);
        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.INVISIBLE);
        c3.setVisibility(View.INVISIBLE);
        c4.setVisibility(View.INVISIBLE);
        c5.setVisibility(View.INVISIBLE);

        rent=findViewById(R.id.RentOSell);
        price=findViewById(R.id.PriceApt);
        bedrooms=findViewById(R.id.NumBed);
        location=findViewById(R.id.Loc);
        gas=findViewById(R.id.GasCon);
        lift=findViewById(R.id.LiftCon);
        secu=findViewById(R.id.SecuCon);
        school=findViewById(R.id.SchoCon);
        hos=findViewById(R.id.HosCon);
        bus=findViewById(R.id.BusCon);
        mEmailShow=findViewById(R.id.EmailShow);
        mSReports=new ArrayList<>();

        mWish=findViewById(R.id.wish);


        final SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                @BaseRating.Smiley int smi= smileRating.getSelectedSmile();

                switch (smiley) {
                    case SmileRating.BAD:
                        level = smileRating.getRating();
                        //Toast.makeText(ExtendSearch.this,"BAD "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        level = smileRating.getRating();
                        //Toast.makeText(ExtendSearch.this,"GOOD "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        level = smileRating.getRating();
                        //Toast.makeText(ExtendSearch.this,"GREAT "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        level = smileRating.getRating();
                        //Toast.makeText(ExtendSearch.this,"OKAY "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.TERRIBLE:
                        level = smileRating.getRating();
                        //Toast.makeText(ExtendSearch.this,"TERRIBLE "+level,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        up=new Upload();
        vf=new UploadValidationForm();
        us=new User();

        mUserDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        ref=FirebaseDatabase.getInstance().getReference("ValidationForm");
        mChatting=FirebaseDatabase.getInstance().getReference("Users");
        final String EimageURL=getIntent().getStringExtra("image");
        String ss=EimageURL;

        //mResultList = (RecyclerView) findViewById(R.id.result_list);
        //mResultList.setHasFixedSize(true);
        //mResultList.setLayoutManager(new LinearLayoutManager(this));

        Log.d("Context: ","Checking Image URL: "+EimageURL);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    //Log.d("Context: ","Im in : ");
                    up=ds.getValue(Upload.class);
                    Log.d("Context: ","All the best : "+up.getmImageUrl());
                    if(up.getmImageUrl().contentEquals(EimageURL))
                    {
                        //Log.d("Context: ","All the best : ");
                        str1=up.getmImageUrl();
                        str2=up.getmImageUrl2();
                        str3=up.getmImageUrl3();
                        str4=up.getmImageUrl4();
                        str5=up.getmLoggedEmail();
                        SendLocation=up.getmLocation();

                        mEmailShow.setText(up.getmEs());
                        rent.setText(up.getmRentOrSell());
                        price.setText(up.getmPrice()+" Tk");
                        bedrooms.setText(up.getmNumber_Of_Bedrooms());
                        location.setText(up.getmLocation());
                        gas.setText(up.getmGas());
                        lift.setText(up.getmLift());
                        secu.setText(up.getmSecurity());
                        school.setText(up.getmSchool());
                        bus.setText(up.getmBus());
                        hos.setText(up.getmHospital());

                        Picasso.with(getApplicationContext()).load(str1).resize(400,400).into(imageView1);
                        Picasso.with(getApplicationContext()).load(str2).resize(400,400).into(imageView2);
                        Picasso.with(getApplicationContext()).load(str3).resize(400,400).into(imageView3);
                        Picasso.with(getApplicationContext()).load(str4).resize(400,400).into(imageView4);

                        //Toast.makeText(ExtendSearch.this, "You sent " + EimageURL, Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                //Toast.makeText(ExtendSearch.this, "You sent " + simageURL, Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    vf=ds.getValue(UploadValidationForm.class);
                    if(vf.getmES().contentEquals(str5))
                    {
                        ReciepientPhone=vf.getmPhone();
                        choiceOnPhone=vf.getmSeeMblNum();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mChatting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    us=ds.getValue(User.class);
                    if(us.getEmail().equalsIgnoreCase(LoggedEmail));
                    {
                        CreatedATEmail=us.getCreatedAt();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*Query firebaseSearchQuery = mUserDatabase.orderByChild("mLocation").startAt("").endAt(""+ "\uf8ff");

        FirebaseRecyclerAdapter<Upload, ExtendSearch.UploadViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Upload, ExtendSearch.UploadViewHolder>(

                Upload.class,
                R.layout.activity_extended_search_result,
                ExtendSearch.UploadViewHolder.class,
                firebaseSearchQuery


        ) {
            @Override
            protected void populateViewHolder(ExtendSearch.UploadViewHolder viewHolder, Upload model, int position) {
                //Log.d("Context: ","Checking Image URL: "+EimageURL);
                if(CountImageURL==0)
                {
                    viewHolder.setDetails(getApplicationContext(), str1,str2,str3,str4);
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

        public void setDetails(Context ctx, String str1, String str2, String str3, String str4){

            ImageView imageView1=mView.findViewById(R.id.image4_1);
            ImageView imageView2=mView.findViewById(R.id.image4_2);
            ImageView imageView3=mView.findViewById(R.id.image4_3);
            ImageView imageView4=mView.findViewById(R.id.image4_4);


            Picasso.with(ctx).load(str1).resize(400,400).into(imageView1);
            Picasso.with(ctx).load(str2).resize(400,400).into(imageView2);
            Picasso.with(ctx).load(str3).resize(400,400).into(imageView3);
            Picasso.with(ctx).load(str4).resize(400,400).into(imageView4);
        }*/

    }
    public void Chat(View v) {

        if(checker==0)
        {
            AlertDialog alertDialog=builderAlertDialog("Sorry!","Your Must Log in First" ,true,ExtendSearch.this);
            alertDialog.show();
            return;
        }
        else
        {
            Intent chatIntent = new Intent(this, SendValidityEmail.class);
            chatIntent.putExtra("RecipientMail",mEmailShow.getText().toString());
            startActivity(chatIntent);
        }

    }
    public void Call(View v) {

        if(checker==0) {
            AlertDialog alertDialog=builderAlertDialog("Sorry!","You must Log in First",true,this);
            alertDialog.show();
        }
        else
        {
            if(choiceOnPhone.equalsIgnoreCase("No"))
            {
                AlertDialog alertDialog=builderAlertDialog("Sorry!","The User Do not Want to be Called",true,ExtendSearch.this);
                alertDialog.show();
            }
            else
            {
                makePhoneCall("",ReciepientPhone);
            }
        }

    }
    public void ViewMap(View v) {
        Intent MapIntent = new Intent(this, Map.class);
        MapIntent.putExtra("ViewLocation",SendLocation);
        startActivity(MapIntent);


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
    private void makePhoneCall(String Sender,String Reciever) {


        if (ContextCompat.checkSelfPermission(ExtendSearch.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ExtendSearch.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else {
            if(Reciever.isEmpty())
            {
                Toast.makeText(ExtendSearch.this,"Sorry the mobile number is not valid",Toast.LENGTH_LONG).show();
                return;
            }
            String dial = "tel:" + Reciever;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }


    }
    public void ReportAdClicked(View v)
    {
        if(checker==0)
        {
            AlertDialog alertDialog=builderAlertDialog("Sorry!","Your must log in first" ,true,ExtendSearch.this);
            alertDialog.show();
            return;
        }
        else
        {
            breport.setVisibility(View.VISIBLE);
            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c4.setVisibility(View.VISIBLE);
            c5.setVisibility(View.VISIBLE);
        }
    }

    public void Rsubmit(View v)
    {
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c1.isChecked())
                {
                    mSReports.add("Violent or Sexual ");
                }
                else
                {
                    mSReports.remove("Violent or Sexual");
                }
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c2.isChecked())
                {
                    mSReports.add("InAppropriate ");
                }
                else
                {
                    mSReports.remove("InAppropriate");
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c3.isChecked())
                {
                    mSReports.add("Abusive ");
                }
                else
                {
                    mSReports.remove("Abusive");
                }
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c4.isChecked())
                {
                    mSReports.add("Fake Post ");
                }
                else
                {
                    mSReports.remove("Fake Post");
                }
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c1.isChecked())
                {
                    mSReports.add("Misbehave when contacted ");
                }
                else
                {
                    mSReports.remove("Misbehave when contacted");
                }
            }
        });

        if(mSReports.isEmpty())
        {
            Toast.makeText(ExtendSearch.this,"Please select at least one issue",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ExtendSearch.this,"Your report is submitted",Toast.LENGTH_SHORT).show();
            favourites report=new favourites(str1);
            String uploadId = mChatting.push().getKey();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mChatting.child(user.getUid()).child("Reported").child(uploadId).setValue(report);
        }
    }

    public void previousView(View v) {
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.showPrevious();
    }

    public void nextView(View v) {
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showNext();
    }
    public void WishPressed(View v) {

        if(checker==0)
        {
            AlertDialog alertDialog=builderAlertDialog("Sorry!","Your must log in first" ,true,ExtendSearch.this);
            alertDialog.show();
            return;
        }
        else
        {
            Toast.makeText(ExtendSearch.this,"Add to Favourites",Toast.LENGTH_SHORT).show();
            int color = 0xFFFF0000;
            mWish.setColorFilter(color);
            favourites fav=new favourites(str1);
            String uploadId = mChatting.push().getKey();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //mChatting.child(user.getUid()).child("Favourites").child(uploadId).setValue(fav);
            mChatting.child(user.getUid()).child("Favourites").child(uploadId).setValue(fav);
        }


    }

}
