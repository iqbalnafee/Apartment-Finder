package com.example.apartment_finder2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ValidationForm extends AppCompatActivity {
    private EditText mFirst_Name,mLast_name,mAge,mOccupation,mNIDorPN;
    private RadioGroup mGroup1,mGroup2;
    private RadioButton mRB1,mRB2;
    private Button mSubmit;
    private TextView mEmail_show;
    String FNstr,LNstr,Agestr,Occstr,NIDstr,mGroup1str,mGroup2str,mESstr;
    private DatabaseReference mDatabaseRef;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_form);
        mFirst_Name = findViewById(R.id.First_Name);
        mLast_name =findViewById(R.id.Last_Name);
        mSubmit=findViewById(R.id.form_submit);
        mEmail_show=findViewById(R.id.email_show);
        mAge=findViewById(R.id.Age);
        mOccupation=findViewById(R.id.occupation);
        mNIDorPN=findViewById(R.id.NIDorPN);
        mGroup1=findViewById(R.id.groupOfRB);
        mGroup2=findViewById(R.id.choice);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ValidationForm");




        /*String simple = "Enter your name ";
        String colored = "                                       *";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mFirst_Name.setText(builder);*/
        final String mEmail = getIntent().getStringExtra("Email");
        str=mEmail;
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });
        mEmail_show.setText(str);
    }
    public void openActivity1() {

        FNstr = mFirst_Name.getText().toString().trim();
        LNstr = mLast_name.getText().toString().trim();
        Agestr= mAge.getText().toString().trim();
        Occstr=mOccupation.getText().toString().trim();
        NIDstr=mNIDorPN.getText().toString().trim();
        mESstr=str;
        if (TextUtils.isEmpty(FNstr)){
            Toast.makeText(ValidationForm.this,"Enter First Name",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(LNstr)){
            Toast.makeText(ValidationForm.this,"Enter Last Name",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Agestr)){
            Toast.makeText(ValidationForm.this,"Enter Date Of Birth",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(mGroup1str)){
            Toast.makeText(ValidationForm.this,"Select Sex",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(Occstr)){
            Toast.makeText(ValidationForm.this,"Enter Occupation",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(NIDstr)){
            Toast.makeText(ValidationForm.this,"Enter NID or Password",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(mGroup2str)){
            Toast.makeText(ValidationForm.this,"Select Property Choice",Toast.LENGTH_SHORT).show();
            return;
        }


        String uploadId = mDatabaseRef.push().getKey();
        UploadValidationForm up = new UploadValidationForm(FNstr,LNstr,Agestr,mGroup1str,Occstr,NIDstr,mGroup2str,mESstr,"Not_Validate_Yet",uploadId);
        mDatabaseRef.child(uploadId).setValue(up);
        Toast.makeText(ValidationForm.this, "Validation Form Upload is successful ", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, waiting.class);
        intent.putExtra("Email",mESstr);
        startActivity(intent);
    }
    public void checkButton1(View v) {
        int radioId=mGroup1.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        mGroup1str= mRB1.getText().toString();
        //Toast.makeText(this,"Selected Sex "+str,Toast.LENGTH_SHORT).show();
    }
    public void checkButton2(View v) {
        int radioId=mGroup2.getCheckedRadioButtonId();
        mRB2=findViewById(radioId);
        mGroup2str= mRB2.getText().toString();
        //Toast.makeText(this,"Selected Sex "+str,Toast.LENGTH_SHORT).show();
    }
}
