package com.example.firebase_practise;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
//    EditText name,department,duration,id;


    ImageView placeimg;
    Button browse,upload;
    Uri imageuri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placeimg=findViewById(R.id.placeimage);
        browse=findViewById(R.id.browse);
        upload=findViewById(R.id.upload);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) { {
                        try {
                            imageuri=uri;
                            InputStream inputStream=getContentResolver().openInputStream(imageuri);
                            bitmap= BitmapFactory.decodeStream(inputStream);
                            placeimg.setImageBitmap(bitmap);
                        }catch (Exception ex){

                        }
                    }
                    }
                });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                                          @Override
                                          public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                              mGetContent.launch("image/*");
                                          }

                                          @Override
                                          public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                          }

                                          @Override
                                          public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                                permissionToken.continuePermissionRequest();
                                          }
                                      }
                        ).check();
            }
        });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadtofirebase();
                }
            });






//        name=findViewById(R.id.name);
//        department=findViewById(R.id.department);
//        duration=findViewById(R.id.duration);
//        id=findViewById(R.id.id);
    }

    private void uploadtofirebase() {
        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Image Uploader");
        dialog.show();
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference uploader=firebaseStorage.getReference().child("image1");
        uploader.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("Uploaded "+(int)percent+"%");
                    }
                });

    }


//    public void buttonclicked(View view) {
//        String variable_name=name.getText().toString().trim();
//        String variable_department=department.getText().toString().trim();
//        String variable_duration=duration.getText().toString().trim();
//        String variable_id=id.getText().toString().trim();
//
//        firebasedbhandler obj=new firebasedbhandler(variable_name,variable_department,variable_duration);
//        FirebaseDatabase db=FirebaseDatabase.getInstance();
//        DatabaseReference node=db.getReference("Student");
//        node.child(variable_id).setValue(obj);
//
//        name.setText("");
//        department.setText("");
//        duration.setText("");
//        id.setText("");
//        Toast.makeText(this, "Record Inserted: ", Toast.LENGTH_SHORT).show();
//
//    }
}