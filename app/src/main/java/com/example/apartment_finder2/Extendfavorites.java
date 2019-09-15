package com.example.apartment_finder2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static com.example.apartment_finder2.MainActivity.LoggedEmail;

public class Extendfavorites extends AppCompatActivity {
    private DatabaseReference mUserDatabase,ref;
    Upload up;
    String simageURL;
    private RecyclerView mResultList;
    User us;
    favourites fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extendfavorites);
        up=new Upload();
        us=new User();
        fav=new favourites();
        mUserDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        ref = FirebaseDatabase.getInstance().getReference("Users");
        mResultList = findViewById(R.id.result_listFav);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("Logged: ",LoggedEmail);
                int c=0;
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    us=ds.getValue(User.class);
                    fav=ds.getValue(favourites.class);
                    if(us.getEmail().equalsIgnoreCase(LoggedEmail))
                    {
                        //c= (int) ds.child("Favourites").getChildrenCount();
                        //String s=ds.child("imageUrl: ").getValue().toString();
                        //String[] parts1 = s.split(",");
                        //String[] parts2 = parts1[0].split("\\{");
                        //firebaseUserSearch(ds.child("Favourites").getValue().toString());
                        //Log.d("EFRT",    s);
                        //Log.d("EFRT",    parts2[1]+" ");
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void firebaseUserSearch(String str) {

        mResultList.setVisibility(View.VISIBLE);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(Search.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("mImageUrl").startAt(str).endAt(str+ "\uf8ff");

        FirebaseRecyclerAdapter<Upload, Extendfavorites.UploadViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Upload, Extendfavorites.UploadViewHolder>(

                Upload.class,
                R.layout.list_layout,
                Extendfavorites.UploadViewHolder.class,
                firebaseSearchQuery

        ) {

            @Override
            public Extendfavorites.UploadViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                final Extendfavorites.UploadViewHolder uploadViewHolder=super.onCreateViewHolder(parent,viewType);
                uploadViewHolder.setOnClickListener(new Search.UploadViewHolder.ClickListener(){
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

            @Override
            protected void populateViewHolder(UploadViewHolder viewHolder, Upload model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getmPrice(), model.getmNumber_Of_Bedrooms(), model.getmImageUrl(),model.getmRentOrSell());
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

        public void setDetails(Context ctx, String Price, String Number_Bedrooms, String userImage, String Sell){

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

        private Search.UploadViewHolder.ClickListener mClickListener;

        public interface ClickListener
        {
            void onItemClick(View view, int position);
        }
        public void setOnClickListener(Search.UploadViewHolder.ClickListener clickListener)
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
}
