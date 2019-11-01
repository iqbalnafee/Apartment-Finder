package com.example.apartment_finder2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
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

public class Admin extends AppCompatActivity {
    private TextView mAdmin_Name;
    private Button mValid,mLogOut;
    String str,str2;
    private RecyclerView mResultList;
    private DatabaseReference mUserDatabase,ref;
    UploadValidationForm up;
    int c=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mAdmin_Name=findViewById(R.id.Admin_Name);
        mValid=findViewById(R.id.ValidCbutton);
        mLogOut=findViewById(R.id.LogOut);

        mResultList = (RecyclerView) findViewById(R.id.result_list1);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        up=new UploadValidationForm();

        final List<String> list = new ArrayList<String>();

        mUserDatabase = FirebaseDatabase.getInstance().getReference("ValidationForm");
        ref = FirebaseDatabase.getInstance().getReference("ValidationForm");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(UploadValidationForm.class);
                    list.add(up.getmES());
                    ++c;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String AdminName = getIntent().getStringExtra("Name");
        str=AdminName;
        mAdmin_Name.setText("WelCome "+str+"!");
    }
    private void firebaseUserSearch(String searchText) {
        //hideKeyboard(Search.this);
        mResultList.setVisibility(View.VISIBLE);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        Query firebaseSearchQuery = mUserDatabase.orderByChild("mES").startAt(searchText).endAt(searchText+ "\uf8ff");

        FirebaseRecyclerAdapter<UploadValidationForm, UploadViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UploadValidationForm, UploadViewHolder>(

                UploadValidationForm.class,
                R.layout.admin_recyler_layout,
                UploadViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(UploadViewHolder viewHolder, UploadValidationForm model, int position) {
                str2=model.getmES();
                viewHolder.setDetails(getApplicationContext(), model.getmES(),"Validate my form");
            }
            @Override
            public UploadViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                final UploadViewHolder uploadViewHolder=super.onCreateViewHolder(parent,viewType);
                uploadViewHolder.setOnClickListener(new Search.UploadViewHolder.ClickListener(){
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Intent intent=new Intent(view.getContext(),CheckValidationForm.class);
                        intent.putExtra("Email",str2);
                        startActivity(intent);
                    }
                });
                return uploadViewHolder;
            }
        };

        mResultList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class UploadViewHolder extends RecyclerView.ViewHolder {

        View mView;
        String sURL;
        public UploadViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    mClickListener.onItemClick(view,getAdapterPosition());
                }
            });

        }

        public void setDetails(Context ctx, String Email, String DefaultText){

            TextView upload_Email = (TextView) mView.findViewById(R.id.ARemail);
            TextView upload_DefaultText = (TextView) mView.findViewById(R.id.ARdefultText);

            upload_Email.setText(Email);
            upload_DefaultText.setText(DefaultText);
            //Glide.with(ctx).load(userImage).into(upload_image);

        }

        private Search.UploadViewHolder.ClickListener mClickListener;

        public interface ClickListener
        {
            void onItemClick(View view, int position);
        }
        public void setOnClickListener(Search.UploadViewHolder.ClickListener clickListener)
        {
            mClickListener=clickListener;
        }

    }





    public void ValidButtonClick(View v) {
            firebaseUserSearch("");
    }
    public void LogOutButtonClick(View v) {

        FirebaseAuth.getInstance().signOut();
        finish();
        MainActivity.checker=0;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
