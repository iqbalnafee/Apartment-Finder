package com.example.apartment_finder2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminUi extends AppCompatActivity {

    private ListView mValidityList;
    private TextView mNotValYet,mCountValNotif;
    private LinearLayout mvalListLayOut;
    DatabaseReference ref;
    UploadValidationForm up;
    int CntValNotif=0;
    String Email="";
    int clickCountOnVal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        clickCountOnVal=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ui);
        //mValidityList=findViewById(R.id.ValidityList);
        mNotValYet=findViewById(R.id.NotValYet);
        mCountValNotif=findViewById(R.id.CountValNotif);
        //mValidityList.setVisibility(View.INVISIBLE);
        ref= FirebaseDatabase.getInstance().getReference("ValidationForm");
        //mvalListLayOut=findViewById(R.id.valListLayOut);
        //mvalListLayOut.setVisibility(View.INVISIBLE);
        mCountValNotif.setVisibility(View.INVISIBLE);
        up=new UploadValidationForm();
        //final List<String> list = new ArrayList<String>();
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    //mValidityList.setVisibility(View.INVISIBLE);
                    up=ds.getValue(UploadValidationForm.class);
                    if(up.getmValidation().equalsIgnoreCase("Not_Validate_Yet"))
                    {
                        ++CntValNotif;
                        //list.add(up.getmES()+"\nplease validate my form");
                    }

                }
                if(CntValNotif>0)
                {
                    mCountValNotif.setVisibility(View.VISIBLE);
                    mCountValNotif.setText(String.valueOf(CntValNotif));
                }
                else
                {
                    mCountValNotif.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*mValidityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String text=mValidityList.getItemAtPosition(index).toString();
                String[] splitStr = text.trim().split("\\s+");
                Intent intent = new Intent(AdminUi.this, CheckValidationForm.class);
                intent.putExtra("Email",splitStr[0]);
                startActivity(intent);
            }
        });*/
    }
    public void ClikedOnVal(View v){


        Intent intent = new Intent(AdminUi.this, ValidityList.class);
        startActivity(intent);


        /*++clickCountOnVal;

        if((clickCountOnVal%2)==1)
        {
            mValidityList.setVisibility(View.VISIBLE);
        }
        else
        {
            mValidityList.setVisibility(View.INVISIBLE);
        }*/
    }
    public void HomeClick(View v)
    {
        Intent intent = new Intent(AdminUi.this, MainActivity.class);
        startActivity(intent);
    }

    public void FClick(View v)
    {
        Intent intent = new Intent(AdminUi.this, Chattting.class);
        startActivity(intent);
    }

}
