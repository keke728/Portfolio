package com.example.keke.pandaface;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BitmapUtils {

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.pandaprovider";

    /**
     * Resample the captured photo to fit the screen for better memory usuage.
     */

    static Bitmap resamplePic(Context context, String imagePath) {

        // get device screen size information
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int targetWidth = metrics.widthPixels;
        int targetHeight = metrics.heightPixels;

        // get the dimensions of the original bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        // determine how much to scale down the image
        int scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight);

        // decode the image file into a Bitmap sized to fill the View
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath);
    }

    /**
     * Creates the temporary image file in the cache directory
     */

    static File createTempImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_hhMMSS",
                Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";
        File storageDir = context.getExternalCacheDir();

        return File.createTempFile(
                imageFileName, /*prefix*/
                ".jpg",  /*suffix*/
                storageDir     /*directory*/
        );
    }

    /**
     * Deletes image file for a given path
     */
    static boolean deleteImageFile(Context context, String imagePath) {

        // get the file
        File imageFile = new File(imagePath);

        // delete the image
        boolean deleted = imageFile.delete();

        // if there is an error deleting the file, show a Toast
        if(!deleted) {
            String errorMessage = "Error finding image";
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
        return deleted;
    }

    /**
     * Helper method for adding the photo to the system photo gallery so it can be accessed from other apps
     */

    private static void addToGallery(Context context, String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imagePath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * Helper method for saving the image
     */

    static String saveImage(Context context, Bitmap image) {

        String savedImagePath = null;

        //create the new file in the external storage
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                           Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/Panda");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdir();
        }

        // Save the new Bitmap
        if(success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream outputStream = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            addToGallery(context, savedImagePath);

            // Show a Toast with the save location
            String savedMessage = "Image Saved!";
            Toast.makeText(context, savedImagePath, Toast.LENGTH_SHORT).show();
        }

        return savedImagePath;
    }

    /** Helper method for sharing an image
     */

    static void shareImage(Context context, String imagePath){

        // create the share intent and start the share activity
        File imageFile = new File(imagePath);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        Uri photoURI = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, imageFile);
        shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
        context.startActivity(shareIntent);
    }
}
