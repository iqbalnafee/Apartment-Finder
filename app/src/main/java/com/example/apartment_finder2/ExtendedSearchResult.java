package com.example.apartment_finder2;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

public class ExtendedSearchResult extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseDatabase mdataBase;
    private RecyclerView mResultList;
    public int CountImg=0;
    //ImageView imageView1,imageView2,imageView3,imageView4;
    String str1,str2,str3,str4;
    Upload up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_search_result);

        up=new Upload();

        mUserDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        final String EimageURL=getIntent().getStringExtra("image");

        mResultList = findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        Log.wtf("Creation", "URL: "+EimageURL);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    //if(CountImg==0) {
                        up = ds.getValue(Upload.class);
                        if (up.getmImageUrl().contentEquals(EimageURL)) {
                            str1 = up.getmImageUrl();
                            str2 = up.getmImageUrl2();
                            str3 = up.getmImageUrl3();
                            str4 = up.getmImageUrl4();

                            break;
                        }
                }


                //}
               // ++CountImg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query firebaseSearchQuery = mUserDatabase.orderByChild("mLocation").startAt("").endAt(""+ "\uf8ff");

        FirebaseRecyclerAdapter<Upload, ExtendedSearchResult.UploadViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Upload, ExtendedSearchResult.UploadViewHolder>(

                Upload.class,
                R.layout.activity_extended_search_result,
                ExtendedSearchResult.UploadViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(ExtendedSearchResult.UploadViewHolder viewHolder, Upload model, int position) {
                    //Toast.makeText(ExtendedSearchResult.this, "You sent " , Toast.LENGTH_LONG).show();
                    viewHolder.setDetails(getApplicationContext(), str1,str2,str3,str4);

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

        public void setDetails(Context ctx, String str1, String str2, String str3,String str4){
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

}
