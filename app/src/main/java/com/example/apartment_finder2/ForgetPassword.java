package com.example.apartment_finder2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    ProgressBar mProgressBar;
    EditText userEmail;
    Button userPass;
    FirebaseAuth firebaseAuth;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mProgressBar=findViewById(R.id.progressBar);
        userEmail=findViewById(R.id.etUserEmail);
        userPass=findViewById(R.id.btnForgotPassword);
        //mToolbar=findViewById(R.id.toolbar);
        //mToolbar.setTitle("ForgotPassword");
        //ForgetPassword.this.setTitle("Forgot Password");
        firebaseAuth=FirebaseAuth.getInstance();
        userPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                mProgressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ForgetPassword.this,"Password send to your email",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(ForgetPassword.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
