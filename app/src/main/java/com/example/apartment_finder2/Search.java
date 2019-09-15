package com.example.apartment_finder2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Search extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private RelativeLayout mR;
    private RecyclerView mResultList;
    private  ListView mlistView;
    private FirebaseDatabase mdataBase;
    String simageURL;

    //private ListViewAdapter adapter;

    private DatabaseReference mUserDatabase,ref;
    Upload up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mlistView=findViewById(R.id.listView);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        mdataBase=FirebaseDatabase.getInstance();
        ref=mdataBase.getReference("uploads");

        mSearchField =  findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        //mResultList.setLayoutManager(new LinearLayoutManager(this));

        final List<String> list = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        up=new Upload();
        //this.arrayAdapterListView();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(Upload.class);
                    list.add(up.getmLocation());
                }
                Set<String> set = new HashSet<>(list);
                list.clear();
                list.addAll(set);
                mlistView.setAdapter(arrayAdapter);
                mlistView.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String text=mlistView.getItemAtPosition(index).toString();
                //Object clickItemObj = adapterView.getAdapter().getItem(index);
                //Toast.makeText(Search.this, "You clicked " + text, Toast.LENGTH_SHORT).show();
                mSearchField.setText(text);
                mlistView.setVisibility(View.INVISIBLE);
                firebaseUserSearch(text);
            }
        });
        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }



            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mResultList.setVisibility(View.INVISIBLE);
                mlistView.setVisibility(View.VISIBLE);
                //hideKeyboard(Search.this);
                ArrayList<String> tempList=new ArrayList<>();

                for(String temp: list)
                {
                    s=s.toString().toLowerCase();
                    if(temp.contains(s))
                    {
                        tempList.add(temp);
                    }
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(Search.this,android.R.layout.simple_list_item_1,tempList);
                mlistView.setAdapter(adapter);
                mlistView.setVisibility(View.VISIBLE);
                String searchText = mSearchField.getText().toString();

                if (TextUtils.isEmpty(searchText))
                {
                    mlistView.setVisibility(View.INVISIBLE);
                    //mResultList.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistView.setVisibility(View.INVISIBLE);
                hideKeyboard(Search.this);
                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);
            }
        });


        mlistView.setVisibility(View.INVISIBLE);
        hideKeyboard(Search.this);
        String searchText = mSearchField.getText().toString();
        firebaseUserSearch(searchText);
    }



    private void firebaseUserSearch(String searchText) {
        hideKeyboard(Search.this);
        mResultList.setVisibility(View.VISIBLE);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(Search.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("mLocation").startAt(searchText).endAt(searchText+ "\uf8ff");

        FirebaseRecyclerAdapter<Upload, UploadViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Upload, UploadViewHolder>(

                Upload.class,
                R.layout.list_layout,
                UploadViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UploadViewHolder viewHolder, Upload model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getmPrice(), model.getmNumber_Of_Bedrooms(), model.getmImageUrl(),model.getmRentOrSell());
            }
            @Override
            public  UploadViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
            {
                final UploadViewHolder uploadViewHolder=super.onCreateViewHolder(parent,viewType);
                uploadViewHolder.setOnClickListener(new UploadViewHolder.ClickListener(){
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        simageURL=uploadViewHolder.getImageURL();
                        Intent intent=new Intent(view.getContext(),ExtendSearch.class);
                        intent.putExtra("image",simageURL);
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

        public void setDetails(Context ctx, String Price, String Number_Bedrooms, String userImage,String Sell){

            RatingBar rate_bar = mView.findViewById(R.id.ratingbar);


            Random rand = new Random();
            // Obtain a number between [0 - 49].
            int n = rand.nextInt(9);
            // Add 1 to the result to get a number from the required range
            // (i.e., [1 - 50]).
            n += 1;

            rate_bar.setRating(10);
            rate_bar.setNumStars(5);
            rate_bar.setProgress(n);
            //Drawable drawable = rate_bar.getProgressDrawable();
            //drawable.setColorFilter(Color.parseColor("#ffd700"), PorterDuff.Mode.SRC_ATOP);


            TextView upload_price = (TextView) mView.findViewById(R.id.PricesOfFlat);
            TextView upload_no_bed = (TextView) mView.findViewById(R.id.bedrooms);
            TextView upload_sellorent = (TextView) mView.findViewById(R.id.SellORent);
            ImageView upload_image = (ImageView) mView.findViewById(R.id.resulted_image);
            sURL=userImage;

            upload_price.setText(Price);
            upload_no_bed.setText(Number_Bedrooms);
            upload_sellorent.setText("For "+Sell);
            Glide.with(ctx).load(userImage).into(upload_image);

        }

        private UploadViewHolder.ClickListener mClickListener;

        public interface ClickListener
        {
            void onItemClick(View view, int position);
        }
        public void setOnClickListener(UploadViewHolder.ClickListener clickListener)
        {
            mClickListener=clickListener;
        }
        public String getImageURL()
        {
            return sURL;
        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void Activity4() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void CSclick(View v)
    {
        Intent intent = new Intent(this, CustomizeSearch.class);
        startActivity(intent);
    }




}