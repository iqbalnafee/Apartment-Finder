package com.example.apartment_finder2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class  ValidityList extends AppCompatActivity {
    private ListView mValidityList;
    DatabaseReference ref;
    UploadValidationForm up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validity_list);
        mValidityList=findViewById(R.id.validityList);
        ref= FirebaseDatabase.getInstance().getReference("ValidationForm");
        final List<String> list = new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    //mValidityList.setVisibility(View.INVISIBLE);
                    up=ds.getValue(UploadValidationForm.class);
                    if(up.getmValidation().equalsIgnoreCase("Not_Validate_Yet"))
                    {
                        list.add(up.getmES()+"\nplease validate my form");
                    }

                }
                Set<String> set = new HashSet<>(list);
                list.clear();
                list.addAll(set);
                mValidityList.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mValidityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String text=mValidityList.getItemAtPosition(index).toString();
                String[] splitStr = text.trim().split("\\s+");
                Intent intent = new Intent(ValidityList.this, CheckValidationForm.class);
                intent.putExtra("Email",splitStr[0]);
                startActivity(intent);
            }
        });
    }
}
