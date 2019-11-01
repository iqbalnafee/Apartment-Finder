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
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.example.apartment_finder2.MainActivity.LoggedEmail;
import static com.example.apartment_finder2.MainActivity.checker;
import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmail,mPassword;
    private TextView mforgotpassword;
    private Button mLogin,mRegister,mGooglelogin;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser mUser;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN=1;
    String Email,Password,AdminName;
    public static final String TAG = "Login";
    ProgressDialog mDialog;
    private DatabaseReference ref;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    User up;
    List<String> list;

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
        ref=FirebaseDatabase.getInstance().getReference("Admins");
        up=new User();
        list = new ArrayList<String>();

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser!=null){
                    //Intent intent = new Intent(Login.this,MainActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent);
                }else {
                    Log.d(TAG,"AuthStateChange:LogOut");
                }
            }
        };*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mGooglelogin.setOnClickListener(this);
        mforgotpassword.setOnClickListener(this);

        mforgotpassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    up=ds.getValue(User.class);
                    list.add(up.getEmail());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
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
        else if(v==mGooglelogin)
        {
            signIn();
        }
    }

    private void userSign() {
        Email = mEmail.getText().toString().trim();
        Password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(Email)) {
            mEmail.setError("Enter Email");
            mEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            mPassword.setError("Enter Password");
            mPassword.requestFocus();
            return;
        }
        mDialog.setMessage("Login please wait...");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                int flag=0;
                if (!task.isSuccessful()){
                    mDialog.dismiss();
                    Toast.makeText(Login.this,"Login not successful "+  task.getException().getMessage(),Toast.LENGTH_LONG).show();

                }else {
                    checker=1;

                    Iterator<String> iter = list.iterator();

                    for(String temp: list)
                    {
                        if(temp.contains(Email))
                        {
                            flag=1;
                            AdminName=iter.next();
                            //AdminName=iter.next();
                            Intent intent = new Intent(Login.this, AdminUi.class);
                            intent.putExtra("Name",AdminName);
                            startActivity(intent);
                            break;
                        }
                    }

                    if(flag==0)
                    {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("Email",Email);
                        LoggedEmail=Email;
                        startActivity(intent);
                        mDialog.dismiss();
                    }
                }
                //flag=0;
            }
        });
    }
}
