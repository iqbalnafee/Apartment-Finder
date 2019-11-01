package com.example.apartment_finder2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Date;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText name,email,password,mobile,address;
    Button mRegisterbtn;
    TextView mLoginPageBack;
    FirebaseAuth mAuth;
    FirebaseDatabase ref;
    DatabaseReference mdatabase;
    String Name,Email,Password,Mobile,Address;
    ProgressDialog mDialog;
    String mToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText)findViewById(R.id.register_name);
        email = (EditText)findViewById(R.id.register_email);
        password = (EditText)findViewById(R.id.register_password);
        mobile = (EditText)findViewById(R.id.mobile);
        address = (EditText)findViewById(R.id.address);

        mRegisterbtn = (Button)findViewById(R.id.register_btn);
        mLoginPageBack = (TextView)findViewById(R.id.loginBackbtn);
        // for auth
        mAuth = FirebaseAuth.getInstance();
        mRegisterbtn.setOnClickListener(this);
        mLoginPageBack.setOnClickListener(this);
        mDialog = new ProgressDialog(this);
        //ref=FirebaseDatabase.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Register.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                mToken = instanceIdResult.getToken();
                Log.e("Token",mToken);
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v==mRegisterbtn){
            UserRegister();
        }else if (v== mLoginPageBack){
            startActivity(new Intent(Register.this,Login.class));
        }
    }

    private void UserRegister() {
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password = password.getText().toString().trim();
        Mobile = mobile.getText().toString().trim();
        Address = address.getText().toString().trim();

        if (TextUtils.isEmpty(Name)) {
            name.setError("Enter User Name");
            name.requestFocus();
            return;
        }else if (TextUtils.isEmpty(Email)){
            email.setError("Enter Email");
            email.requestFocus();
            return;
        }else if (TextUtils.isEmpty(Password)){
            password.setError("Enter Password");
            password.requestFocus();
            return;
        }else if (Password.length()<6){
            password.setError("Password must be greater than 6");
            password.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Mobile)){
            mobile.setError("Enter Mobile Number");
            mobile.requestFocus();
            return;
        }
        else if (Mobile.length()<10){
            mobile.setError("Mobile Number be greater than 10");
            mobile.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(Address)){
            address.setError("Enter Address");
            address.requestFocus();
            return;
        }

        mDialog.setMessage("Creating User please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendEmailVerification();
                    mDialog.dismiss();
                    //LoggedName=
                    OnAuth(task.getResult().getUser());
                    //mAuth.signOut();
                }else{
                    Toast.makeText(Register.this,"error on creating user",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register.this,"Check your Email for verification",Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent1 = new Intent(Register.this, MainActivity.class);
                        //Toast.makeText(MainActivity.this, "You sent " + str, Toast.LENGTH_LONG).show();
                        startActivity(intent1);
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {
        User user = BuildNewuser();
        mdatabase.child(uid).setValue(user);
    }


    private User BuildNewuser(){

        return new User(
                getDisplayName(),
                getUserEmail(),
                new Date().getTime(),
                mToken,
                Mobile,
                Address
        );
    }

    public String getDisplayName() {
        return Name;
    }

    public String getUserEmail() {
        return Email;
    }

}
