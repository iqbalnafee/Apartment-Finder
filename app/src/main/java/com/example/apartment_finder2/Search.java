package com.example.apartment_finder2;

import android.app.Activity;
import android.content.Context;
import android.database.Observable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {

    //private EditText mSearchField;
    private AutoCompleteTextView mSearchField;
    private ImageButton mSearchBtn;
    private ListView list;
    private RelativeLayout mR;
    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;
    //Upload up=new Upload();
    String Location[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mR = findViewById(R.id.ClickOnR);
        mUserDatabase = FirebaseDatabase.getInstance().getReference("uploads");

        mSearchField = (AutoCompleteTextView) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));



        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(Search.this);
                String searchText = mSearchField.getText().toString();
                //Matcher m = Pattern.compile(Pattern.quote(mSearchField.getText().toString()), Pattern.CASE_INSENSITIVE).matcher("");
                //m.reset(searchText);
                firebaseUserSearch(searchText);

            }
        });

    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(Search.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("mLocation").startAt(searchText).endAt(searchText+ "\uf8ff");

        FirebaseRecyclerAdapter<Upload, UploadViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Upload, UploadViewHolder>(

                Upload.class,
                R.layout.list_layout,
                UploadViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UploadViewHolder viewHolder, Upload model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getmPrice(), model.getmNumber_Of_Bedrooms(), model.getmImageUrl());
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

        public void setDetails(Context ctx, String Price, String Number_Bedrooms, String userImage){

            TextView upload_price = (TextView) mView.findViewById(R.id.PricesOfFlat);
            TextView upload_no_bed = (TextView) mView.findViewById(R.id.bedrooms);
            ImageView upload_image = (ImageView) mView.findViewById(R.id.resulted_image);


            upload_price.setText(Price);
            upload_no_bed.setText(Number_Bedrooms);
            Glide.with(ctx).load(userImage).into(upload_image);



        }

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}