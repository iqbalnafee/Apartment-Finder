package com.example.apartment_finder2;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class Advertisement extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName,mPrice,mBedrooms,mLoc;
    private ImageView mImageView1,mImageView2,mImageView3,mImageView4;
    private ProgressBar mProgressBar,mProgressBar1,mProgressBar2,mProgressBar3,mProgressBar4;
    private int count=1;

    String Prices,Bedrooms,Loc,mDownloadUrl1,mDownloadUrl2,mDownloadUrl3,mDownloadUrl4;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);



        //mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        //mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        //mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mPrice = findViewById(R.id.flat_price);
        mBedrooms = findViewById(R.id.no_of_bedRooms);
        mLoc = findViewById(R.id.location);
        //mImageView1 = findViewById(R.id.image_to_upload1);
        //mImageView2 = findViewById(R.id.image_to_upload2);
        //mImageView3 = findViewById(R.id.image_to_upload3);
        //mImageView4 = findViewById(R.id.image_to_upload4);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar1 = findViewById(R.id.progress_bar1);
        mProgressBar2 = findViewById(R.id.progress_bar2);
        mProgressBar3 = findViewById(R.id.progress_bar3);
        mProgressBar4 = findViewById(R.id.progress_bar4);


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

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

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask != null && mUploadTask.isInProgress()&&count<4) {
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
                                    Toast.makeText(Advertisement.this, "Upload successful", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(Advertisement.this, "Upload successful", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(Advertisement.this, "Upload successful", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(Advertisement.this, "Upload successful", Toast.LENGTH_LONG).show();
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
        String s=Loc.toLowerCase();

        Upload upload = new Upload(Prices,Bedrooms,s,mDownloadUrl1,mDownloadUrl2,mDownloadUrl3,mDownloadUrl4);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload);
        Toast.makeText(Advertisement.this, "Upload in DataBase is successful ", Toast.LENGTH_LONG).show();
    }

}
