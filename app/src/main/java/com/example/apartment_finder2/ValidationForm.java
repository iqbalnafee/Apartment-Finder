package com.example.apartment_finder2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ValidationForm extends AppCompatActivity {
    private EditText mFirst_Name,mLast_name,mAge,mOccupation,mNIDorPN,mMbl,mPermanent;
    private RadioGroup mGroup1,mGroup2;
    private RadioButton mRB1,mRB2;
    private Button mSubmit;
    private TextView mEmail_show;
    String FNstr,LNstr,Agestr,Occstr,NIDstr,mGroup1str,mGroup2str,mESstr,MobileTel,mDownloadUrl1,mDownloadUrl2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference mDatabaseRef;
    String str;
    private ImageView mImageView1,mImageView2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private int count=1;
    private Uri mImageUri;
    private ProgressBar mProgressBar1,mProgressBar2;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
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
        mMbl=findViewById(R.id.Mbl);
        mImageView1 = findViewById(R.id.FrontNID);
        mImageView2 = findViewById(R.id.BackNID);
        mProgressBar1 = findViewById(R.id.FrontNIDProgBar);
        mProgressBar2 = findViewById(R.id.BackNIDProgBar);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ValidationForm");
        mStorageRef = FirebaseStorage.getInstance().getReference("ValidationForm");

        mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        mAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(ValidationForm.this);
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ValidationForm.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                mAge.setText(date);
            }
        };

        //mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        //mMbl.setVisibility(View.INVISIBLE);
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
        MobileTel=mMbl.getText().toString().trim();
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
            Toast.makeText(ValidationForm.this,"Enter Permanent Address",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(MobileTel)){
            Toast.makeText(ValidationForm.this,"Enter Telephone Or Phone Number",Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(mGroup2str)){
            Toast.makeText(ValidationForm.this,"Enter Whether Client can Call you or not",Toast.LENGTH_SHORT).show();
            return;
        }


        String uploadId = mDatabaseRef.push().getKey();
        UploadValidationForm up = new UploadValidationForm(FNstr,LNstr,Agestr,mGroup1str,Occstr,NIDstr,MobileTel,mGroup2str,mDownloadUrl1,mDownloadUrl2,mESstr,"Not_Validate_Yet",uploadId);
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
    }
    public void checkButton2(View v) {
        int radioId=mGroup2.getCheckedRadioButtonId();
        mRB2=findViewById(radioId);
        mGroup2str= mRB2.getText().toString();
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            if(count==1)
            {
                Picasso.with(this).load(mImageUri).into(mImageView1);
                if (mImageUri != null) {
                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful());
                                    Uri downloadUrl = urlTask.getResult();
                                    mDownloadUrl1=downloadUrl.toString();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar1.setProgress(0);
                                        }
                                    }, 1000000000);
                                    Toast.makeText(ValidationForm.this, "Upload successful", Toast.LENGTH_LONG).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar1.setProgress((int) progress);
                                }
                            });}
            }
            else if(count==2)
            {
                Picasso.with(this).load(mImageUri).into(mImageView2);
                if (mImageUri != null) {
                    final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    mUploadTask = fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful());
                                    Uri downloadUrl = urlTask.getResult();
                                    mDownloadUrl2=downloadUrl.toString();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar2.setProgress(0);
                                        }
                                    }, 1000000000);
                                    Toast.makeText(ValidationForm.this, "Upload successful", Toast.LENGTH_LONG).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar2.setProgress((int) progress);
                                }
                            });}
            }

            ++count;

        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
