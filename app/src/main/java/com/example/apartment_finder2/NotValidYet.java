package com.example.apartment_finder2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotValidYet extends AppCompatActivity {
    String str;
    private TextView mTitle1,mTitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_valid_yet);
        final String mEmail = getIntent().getStringExtra("Email");
        str=mEmail;
    }
}
