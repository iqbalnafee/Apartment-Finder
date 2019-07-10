package com.example.apartment_finder2;

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
        mTitle1.setText("Thank you "+str+"\n For Uploading your valuable information");
        mTitle2.setText("Please wait for approval");
    }
}
