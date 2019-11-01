package com.example.apartment_finder2;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.apartment_finder2.MainActivity.LoggedEmail;

public class FeedbackForm extends AppCompatActivity {
    int level=999;
    String FeedbackCategoryString,FeedbackMessageString;
    EditText mFm;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        mFm=findViewById(R.id.FeedbackMessage);
        final SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Feedback");

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
                @BaseRating.Smiley int smi= smileRating.getSelectedSmile();

                switch (smiley) {
                    case SmileRating.BAD:
                        level = smileRating.getRating();
                        //Toast.makeText(FeedbackForm.this,"BAD "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        level = smileRating.getRating();
                        //Toast.makeText(FeedbackForm.this,"GOOD "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        level = smileRating.getRating();
                        //Toast.makeText(FeedbackForm.this,"GREAT "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        level = smileRating.getRating();
                        //Toast.makeText(FeedbackForm.this,"OKAY "+level,Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.TERRIBLE:
                        level = smileRating.getRating();
                        //Toast.makeText(FeedbackForm.this,"TERRIBLE "+level,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    public void Sclick(View v) {
        FeedbackCategoryString="Suggestion";
        Toast.makeText(FeedbackForm.this,"You Select \"Suggestion\" ",Toast.LENGTH_SHORT).show();
    }
    public void NRclick(View v) {
        FeedbackCategoryString="Not Right";
        Toast.makeText(FeedbackForm.this,"You Select \"Something is not Right\" ",Toast.LENGTH_SHORT).show();
    }
    public void Cclick(View v) {
        FeedbackCategoryString="Compliment";
        Toast.makeText(FeedbackForm.this,"You Select \"Compliment\" ",Toast.LENGTH_SHORT).show();
    }
    public void SendClick(View v) {
        FeedbackMessageString=mFm.getText().toString().trim();

        if (level==999){
            Toast.makeText(FeedbackForm.this,"Select Opinoin Emojis",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(FeedbackCategoryString)){
            Toast.makeText(FeedbackForm.this,"Select Feedback Category",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(FeedbackMessageString)){
            Toast.makeText(FeedbackForm.this,"Enter Feedback Message",Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar1=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
        String currenttime = simpleDateFormat.format(calendar1.getTime());

        Calendar calendar2 = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar2.getTime());

        String Time=currenttime+" "+currentDate;

        UploadFeedbackForm upload = new UploadFeedbackForm(LoggedEmail,level,FeedbackCategoryString,FeedbackMessageString,Time);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload);

        AlertDialog alertDialog=builderAlertDialog("Thanks!","Your Feedback Form is Received"
                ,true,FeedbackForm.this);
        alertDialog.show();
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
}
