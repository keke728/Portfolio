package com.example.keke.pandaface;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.pandaprovider";

    private ImageView imageView;
    private ImageButton cameraBtn;
    private FloatingActionButton shareFab;
    private FloatingActionButton saveFab;
    private FloatingActionButton clearFab;

    private String temPhotoPath;
    private Bitmap resultBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the views
        imageView = findViewById(R.id.image_view);
        cameraBtn = findViewById(R.id.cameraBtn);
        saveFab = findViewById(R.id.save_btn);
        shareFab = findViewById(R.id.share_btn);
        clearFab = findViewById(R.id.clear_btn);
    }

    /**
     * Onclick method to launch the camera
     */

    public void PandaMe(View view){
        // Check for the external storage permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // Launch the camera if the permission exists
            launchCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // if you get permission, launch the camera
                    launchCamera();
                } else {
                    // if you do not get permission, show a Toast
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    /**
     * Creates a temporary image file and captures a picture to store in it
     */

    private void launchCamera(){

        // create the capture image intent
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // ensure that there's a camera activity to handle the intent
        if(takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            // create the temporary file where the photo should go
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(this);
            }catch (IOException ex){
                // error occurred while creating the File
                ex.printStackTrace();
            }
            // continue only if the File was successfully created
            if(photoFile != null){

                // get the path of the temporary file
                temPhotoPath = photoFile.getAbsolutePath();

                // get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // add the URI so the camera can store the image
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // launch the camera activity
                startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // if the image capture activity was called and was successful
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // process the image and set it to the TextView
            processAndSetImage();
        } else {

            // otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, temPhotoPath);
        }

    }


    /**
     *  Method for processing the captured image and setting it to the ImageView
     */

    private void processAndSetImage(){

        //Toggle Visibility of the views
        cameraBtn.setVisibility(View.GONE);
        saveFab.setVisibility(View.VISIBLE);
        shareFab.setVisibility(View.VISIBLE);
        clearFab.setVisibility(View.VISIBLE);

        //Resample the saved image to fit the ImageView
        resultBitmap = BitmapUtils.resamplePic(this, temPhotoPath);

        //Detect the faces and overlay the appropriate panda emoji
        resultBitmap = Panda.detectFacesandOverlayEmoji(this, resultBitmap);

        //Set the new bitmap to the ImageView
        imageView.setImageBitmap(resultBitmap);
    }

    /**
     * OnClick method for save button
     */

    public void savePhoto(View view){

        // delete the temporary photo file
        BitmapUtils.deleteImageFile(this, temPhotoPath);

        // save the photo
        BitmapUtils.saveImage(this, resultBitmap);
    }

    /**
     * OnClick method for the share button
     */

    public void sharePhoto(View view){

        // delete the temporary photo file
        BitmapUtils.deleteImageFile(this, temPhotoPath);

        // save the photo
        BitmapUtils.saveImage(this, resultBitmap);

        // share the image
        BitmapUtils.shareImage(this, temPhotoPath);
    }

    /**
     * OnClick method for the clear button
     */

    public void clearPhoto(View view){

        // clear the image and toggle the view visibility
        imageView.setImageResource(0);
        cameraBtn.setVisibility(View.VISIBLE);
        shareFab.setVisibility(View.GONE);
        saveFab.setVisibility(View.GONE);
        clearFab.setVisibility(View.GONE);

        // delete the temporary image file
        BitmapUtils.deleteImageFile(this, temPhotoPath);
    }
}
