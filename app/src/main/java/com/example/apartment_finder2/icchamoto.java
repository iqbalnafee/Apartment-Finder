package com.example.apartment_finder2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.apartment_finder2.MainActivity.LoggedEmail;

public class icchamoto extends AppCompatActivity {
    private EditText field1,field2;
    String name,age;

    DatabaseReference MockInsert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icchamoto);
        field1=findViewById(R.id.Cname);
        field2=findViewById(R.id.Cage);
        MockInsert= FirebaseDatabase.getInstance().getReference("SubmissionMockData");

    }

    public void GoSomeWhere(View v)
    {
        name=field1.getText().toString();
        age=field2.getText().toString();
        Log.d("The_Inputs: ",name+" "+age);
        SubmissionMockData submitData=new SubmissionMockData(name,age);
        String uploadId = MockInsert.push().getKey();
        MockInsert.child(uploadId).setValue(submitData);
    }
}
