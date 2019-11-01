package com.example.apartment_finder2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class waiting extends AppCompatActivity {
    String str;
    private TextView mTitle1,mTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        final String mEmail = getIntent().getStringExtra("Email");
        str=mEmail;
        mTitle1=findViewById(R.id.title1);
        mTitle2=findViewById(R.id.title2);
        mTitle1.setText("Thanks you for uploading your valuable information");
        mTitle2.setText("Please wait for approval");

        /*String recipientList = str;
        String[] recipients = recipientList.split(",");

        String subject = "Thank you \"+str+\"\\n For Uploading your valuable information";
        String message = "Please wait for approval";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));*/

    }
}
