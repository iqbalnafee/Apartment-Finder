package com.example.apartment_finder2;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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

import static com.example.apartment_finder2.MainActivity.LoggedEmail;
import static com.example.apartment_finder2.MainActivity.checker;

public class Advertisement extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;


    private RadioGroup mGroup1,mGroup2,mGroup3,mGroup4,mGroup5,mGroup6;
    private RadioButton mRB1,mRB2,mRB3,mRB4,mRB5,mRB6;
    String Group1StrRentSell,Group2StrGas,Group3StrLift,Group4StrAmenities,Group5StrSchool,Group6StrHospital,mSchoolStr,mHospitalStr,mBusStr;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName,mPrice,mBedrooms,mLoc,mSchool,mHospital,mBus;
    private ImageView mImageView1,mImageView2,mImageView3,mImageView4;
    private ProgressBar mProgressBar,mProgressBar1,mProgressBar2,mProgressBar3,mProgressBar4;
    public int count=1;
    int track=1;
    String Prices,Bedrooms,Loc,mDownloadUrl1,mDownloadUrl2,mDownloadUrl3,mDownloadUrl4;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        mGroup1=findViewById(R.id.groupOfRentSell);
        mGroup2=findViewById(R.id.groupOfGas);
        mGroup3=findViewById(R.id.groupOfLift);
        mGroup4=findViewById(R.id.groupOfAmenities);
        mGroup5=findViewById(R.id.groupOfSchool);
        mGroup6=findViewById(R.id.groupOfHospital);

        mButtonUpload = findViewById(R.id.button_upload);
        mPrice = findViewById(R.id.flat_price);
        mBedrooms = findViewById(R.id.no_of_bedRooms);
        mLoc = findViewById(R.id.locations2);
        mSchool=findViewById(R.id.SchoolName);
        mHospital=findViewById(R.id.HospitalName);
        mBus=findViewById(R.id.BusStandName);

        mImageView1 = findViewById(R.id.image_to_upload1);
        mImageView2 = findViewById(R.id.image_to_upload2);
        mImageView3 = findViewById(R.id.image_to_upload3);
        mImageView4 = findViewById(R.id.image_to_upload4);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar1 = findViewById(R.id.progress_bar1);
        mProgressBar2 = findViewById(R.id.progress_bar2);
        mProgressBar3 = findViewById(R.id.progress_bar3);
        mProgressBar4 = findViewById(R.id.progress_bar4);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mSchool.setVisibility(View.INVISIBLE);
        mHospital.setVisibility(View.INVISIBLE);

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
        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        //mButtonUpload.setVisibility(View.INVISIBLE);

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count<4) {
                    Toast.makeText(Advertisement.this, "Please Upload all 4 images or Wait For Upload to all", Toast.LENGTH_SHORT).show();
                } else {
                    uploadInfoInDatabase();
                }

            }
        });

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
                                    Toast.makeText(Advertisement.this, "Uploaded first image", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(Advertisement.this, "Uploaded second image", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar2.setProgress((int) progress);
                                }
                            });}
            }
            else if(count==3)
            {
                Picasso.with(this).load(mImageUri).into(mImageView3);
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
                                    mDownloadUrl3=downloadUrl.toString();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar3.setProgress(0);
                                        }
                                    }, 1000000000);
                                    Toast.makeText(Advertisement.this, "Uploaded third image", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar3.setProgress((int) progress);
                                }
                            });}
            }
            else if(count==4)
            {
                Picasso.with(this).load(mImageUri).into(mImageView4);
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
                                    mDownloadUrl4=downloadUrl.toString();
                                    //Toast.makeText(Advertisement.this, "You sent " + mDownloadUrl4, Toast.LENGTH_LONG).show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar4.setProgress(0);
                                        }
                                    }, 1000000000);
                                    Toast.makeText(Advertisement.this, "Upload fourth image", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar4.setProgress((int) progress);
                                }
                            });}
            }
            ++count;

        }
    }


    private void uploadInfoInDatabase()
    {
        Prices = mPrice.getText().toString().trim();
        Bedrooms = mBedrooms.getText().toString().trim();
        Loc = mLoc.getText().toString().trim();
        mSchoolStr=mSchool.getText().toString().trim();
        mHospitalStr=mHospital.getText().toString().trim();
        mBusStr=mBus.getText().toString().trim();
        String s=Loc.toLowerCase();

        if (TextUtils.isEmpty(Prices)) {
            mPrice.setError("Enter Price in ৳");
            mPrice.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Bedrooms)) {
            mBedrooms.setError("Enter Bedrooms");
            mBedrooms.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Loc)) {
            mLoc.setError("Enter Location");
            mLoc.requestFocus();
            return;
        }


        Upload upload = new Upload(LoggedEmail,Prices,Bedrooms,s,mDownloadUrl1,mDownloadUrl2,mDownloadUrl3,mDownloadUrl4,mSchoolStr,mHospitalStr,
                mBusStr,Group2StrGas,Group3StrLift,Group4StrAmenities,Group1StrRentSell,LoggedEmail);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload);
        Toast.makeText(Advertisement.this, "Upload in DataBase is successful ", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(Advertisement.this,MainActivity.class);
        startActivity(intent);
    }
    public void RentClicked(View v) {
        int radioId=mGroup1.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group1StrRentSell= mRB1.getText().toString();
    }
    public void SellClicked(View v) {
        int radioId=mGroup1.getCheckedRadioButtonId();
        mRB2=findViewById(radioId);
        Group1StrRentSell= mRB2.getText().toString();
    }
    public void GasClickedYes(View v) {
        int radioId=mGroup2.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group2StrGas= mRB1.getText().toString();

    }
    public void GasClickedNo(View v) {
        int radioId=mGroup2.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group2StrGas= mRB1.getText().toString();

    }
    public void LiftClickedYes(View v) {
        int radioId=mGroup3.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group3StrLift= mRB1.getText().toString();
    }
    public void LiftClickedNo(View v) {
        int radioId=mGroup3.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group3StrLift= mRB1.getText().toString();
    }
    public void AmenitiesClickedYes(View v) {
        int radioId=mGroup4.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group4StrAmenities= mRB1.getText().toString();
    }
    public void AmenitiesClickedNo(View v) {
        int radioId=mGroup4.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group4StrAmenities= mRB1.getText().toString();
    }
    public void SchoolClickedYes(View v) {
        mSchool.setVisibility(View.VISIBLE);
        int radioId=mGroup5.getCheckedRadioButtonId();
        mRB2=findViewById(radioId);
        Group5StrSchool= mRB2.getText().toString();
    }
    public void SchoolClickedNo(View v) {
        mSchool.setText("");
        mSchool.setVisibility(View.INVISIBLE);
        int radioId=mGroup5.getCheckedRadioButtonId();
        mRB2=findViewById(radioId);
        Group5StrSchool= mRB2.getText().toString();
    }
    public void HospitalClickedYes(View v) {
        mHospital.setVisibility(View.VISIBLE);
        int radioId=mGroup6.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group6StrHospital= mRB1.getText().toString();
    }
    public void HospitalClickedNo(View v) {
        mHospital.setText("");
        mHospital.setVisibility(View.INVISIBLE);
        int radioId=mGroup6.getCheckedRadioButtonId();
        mRB1=findViewById(radioId);
        Group6StrHospital= mRB1.getText().toString();
    }


}
