package com.example.apartment_finder2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
//import com.squareup.picasso.Picasso;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
//import com.squareup.picasso.Picasso;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

import static com.example.apartment_finder2.MainActivity.checker;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmail,mPassword;
    private TextView mforgotpassword;
    private Button mLogin,mRegister,mGooglelogin;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser mUser;

    String Email,Password;
    public static final String TAG = "Login";
    ProgressDialog mDialog;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mEmail = (EditText)findViewById(R.id.login_emailId);
        mPassword = (EditText)findViewById(R.id.login_password);
        mforgotpassword = (TextView)findViewById(R.id.forgotPassword);
        mLogin = (Button)findViewById(R.id.sign_in);
        mRegister = (Button)findViewById(R.id.sign_up);
        mGooglelogin = (Button)findViewById(R.id.google_button);
        mDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser!=null){
                    /*Intent intent = new Intent(Login.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                }else {
                    Log.d(TAG,"AuthStateChange:LogOut");
                }
            }
        };



        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mGooglelogin.setOnClickListener(this);
        mforgotpassword.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        Login.super.finish();
    }

    @Override
    public void onClick(View v) {
        if (v==mLogin){
            userSign();
        }
        else if (v==mRegister){
            startActivity(new Intent(Login.this,Register.class));
        }
    }

    private void userSign() {
        Email = mEmail.getText().toString().trim();
        Password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(Login.this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Password)){
            Toast.makeText(Login.this,"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Login please wait...");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    mDialog.dismiss();
                    Toast.makeText(Login.this,"Login not successful",Toast.LENGTH_LONG).show();

                }else {

                    checker=1;

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                    mDialog.dismiss();
                }
            }
        });
    }
}
