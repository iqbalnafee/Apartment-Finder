package com.example.apartment_finder2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.bumptech.glide.Glide;
import com.example.apartment_finder2.ChatAdapter.MessageChatAdapter;
import com.example.apartment_finder2.ChatHelper.ExtraIntent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

import static com.example.apartment_finder2.MainActivity.LoggedEmail;
import static com.example.apartment_finder2.MainActivity.checker;

public class ExtendSearch extends AppCompatActivity {

    private TextView rent,price,bedrooms,location,gas,lift,secu,school,hos,bus;

    private DatabaseReference mUserDatabase;
    private DatabaseReference ref,mChatting;
    private FirebaseDatabase mdataBase;
    private RecyclerView mResultList;
    public int CountImageURL=0;
    private static final int REQUEST_CALL = 1;
    private CheckBox c1,c2,c3,c4,c5;
    private Button breport;

    //ImageView imageView1,imageView2,imageView3,imageView4;
    String str1,str2,str3,str4,str5,ReciepientPhone,choiceOnPhone;
    long CreatedATEmail;
    Upload up;
    UploadValidationForm vf;
    User us;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_search);

        breport=findViewById(R.id.ReportSubmitted);

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
        mChatting=FirebaseDatabase.getInstance().getReference("User");
        final String EimageURL=getIntent().getStringExtra("image");
        String ss=EimageURL;

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(Upload.class);
                    if(up.getmImageUrl().contentEquals(EimageURL))
                    {
                        str1=up.getmImageUrl();
                        str2=up.getmImageUrl2();
                        str3=up.getmImageUrl3();
                        str4=up.getmImageUrl4();
                        str5=up.getmLoggedEmail();

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
                        //Log.d("Context: ","Checking Image URL: "+EimageURL);
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
        Query firebaseSearchQuery = mUserDatabase.orderByChild("mLocation").startAt("").endAt(""+ "\uf8ff");
        //Query firebaseSearchQuery1 = mUserDatabase.orderByChild("EimageURL").startAt("").endAt(""+ "\uf8ff");

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


            Picasso.with(ctx).load(str1).resize(200,200).into(imageView1);
            Picasso.with(ctx).load(str2).resize(200,200).into(imageView2);
            Picasso.with(ctx).load(str3).resize(200,200).into(imageView3);
            Picasso.with(ctx).load(str4).resize(200,200).into(imageView4);
        }

    }
    public void Chat(View v) {
        Intent chatIntent = new Intent(this, SendValidityEmail.class);
        startActivity(chatIntent);

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
            String dial = "tel:" + Reciever;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }


    }
    public void ReportAdClicked(View v)
    {
        breport.setVisibility(View.VISIBLE);
        c1.setVisibility(View.VISIBLE);
        c2.setVisibility(View.VISIBLE);
        c3.setVisibility(View.VISIBLE);
        c4.setVisibility(View.VISIBLE);
        c5.setVisibility(View.VISIBLE);
    }

}
