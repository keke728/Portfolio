package com.example.keke.pandaface;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;


public class Panda {

    private static final String LOG_TAG = Panda.class.getSimpleName();
    private static final float PANDA_SCALE_FACTOR = .95f;
    private static final double SMILING_PROB_THRESHOLD = .15;
    private static final double EYE_OPEN_PROB_THRESHOLD = .5;

    /**
     * Method for detecting faces in a bitmap
     */

    static Bitmap detectFacesandOverlayEmoji(Context context, Bitmap photo) {

        // create the face detector , disable tracking and enable classifications
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        // build the frame
        Frame frame = new Frame.Builder().setBitmap(photo).build();

        // detect the faces
        SparseArray<Face> faces = detector.detect(frame);

        // log the number of faces
        Log.d(LOG_TAG, "Detected Faces = " + faces.size());

        // initialize result bitmap to original picture
        Bitmap resultBitmap = photo;

        // if there are no faces detected, show a Toast message
        if (faces.size() == 0) {
            Toast.makeText(context, "No Faces Detected", Toast.LENGTH_SHORT).show();
        } else {

            // iterate through the faces
            for (int i = 0; i < faces.size(); ++i) {
                Face face = faces.valueAt(i);
                Bitmap pandabitmap;
                switch (whichFace(face)) {
                    case SMILE:
                        pandabitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.smile);
                        break;
                    case FROWN:
                        pandabitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.frown);
                        break;
                    case LEFT_WINK:
                        pandabitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.leftwink);
                        break;
                    case RIGHT_WINK:
                        pandabitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.rightwink);
                        break;
                    case CLOSED_EYE_SMILE:
                        pandabitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.closed_smile);
                        break;
                    case CLOSED_EYE_FROWN:
                        pandabitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.closed_frown);
                        break;
                    default:
                        pandabitmap = null;
                        Toast.makeText(context, R.string.no_emoji, Toast.LENGTH_SHORT).show();
                }

                // add the bitmap to the proper position in the original image
                resultBitmap = addBitmapToFace(resultBitmap, pandabitmap, face);
            }
        }

        // Release the detector
        detector.release();

        return resultBitmap;
    }

    /**
     * Determines the closest panda emoji to the expression on the face , based on
     * the odds that the person is smiling and has each eye open
     */

    private static PandaFace whichFace(Face face) {

        // log all the probabilities
        Log.d(LOG_TAG, "whichFace: smilingProb = " + face.getIsSmilingProbability());
        Log.d(LOG_TAG, "whichFace: leftEyeOpenProb = " + face.getIsLeftEyeOpenProbability());
        Log.d(LOG_TAG, "whichFae: rightEyeOpenProb = " + face.getIsRightEyeOpenProbability());

        boolean smiling = face.getIsSmilingProbability() > SMILING_PROB_THRESHOLD;
        boolean leftEyeClosed = face.getIsLeftEyeOpenProbability() < EYE_OPEN_PROB_THRESHOLD;
        boolean rightEyeClosed = face.getIsRightEyeOpenProbability() < EYE_OPEN_PROB_THRESHOLD;

        // Determines and log the appropriate panda face
        PandaFace pface;
        if (smiling) {
            if (leftEyeClosed && !rightEyeClosed) {
                pface = PandaFace.LEFT_WINK;
            } else if (rightEyeClosed && !leftEyeClosed) {
                pface = PandaFace.RIGHT_WINK;
            } else if (leftEyeClosed) {
                pface = PandaFace.CLOSED_EYE_SMILE;
            } else {
                pface = PandaFace.SMILE;
            }
        } else {
            if (leftEyeClosed && rightEyeClosed) {
                pface = PandaFace.CRY;
            } else if (leftEyeClosed) {
                pface = PandaFace.CLOSED_EYE_FROWN;
            } else {
                pface = PandaFace.FROWN;
            }
        }

        // Log the chosen panda face
        Log.d(LOG_TAG, "whichFace: " + pface.name());
        return pface;
    }

    /**
     * Combines the original photo with the pface bitmaps
     */

    private static Bitmap addBitmapToFace(Bitmap bgBitmap, Bitmap pandaBitmap, Face face) {

        // initialize the results bitmap to be a mutable copy of the original photo
        Bitmap resultBitmap = Bitmap.createBitmap(bgBitmap.getWidth(),
                bgBitmap.getHeight(), bgBitmap.getConfig());

        // determine the size of the panda emoji to match the width of the face and preserve aspect ratio
        int newEmojiW = (int) (face.getWidth() * PANDA_SCALE_FACTOR);
        int newEmojiH = (int) (pandaBitmap.getHeight() * newEmojiW /
                pandaBitmap.getWidth() * PANDA_SCALE_FACTOR);

        // scale the emoji
        pandaBitmap = Bitmap.createScaledBitmap(pandaBitmap, newEmojiW, newEmojiH, false);

        // determine the emoji position so it best lines up with the face

        float emojiPositionX = (face.getPosition().x + face.getWidth() / 2) - pandaBitmap.getWidth() / 2;
        float emojiPositionY = (face.getPosition().y + face.getHeight() / 2) - pandaBitmap.getHeight() / 3;

        // create the canvas and draw the bitmaps to it
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        canvas.drawBitmap(pandaBitmap, emojiPositionX, emojiPositionY, null);

        return resultBitmap;
    }


    // Enum for all possible faces
        private enum PandaFace {
            CRY,
            SMILE,
            FROWN,
            LEFT_WINK,
            RIGHT_WINK,
            CLOSED_EYE_SMILE,
            CLOSED_EYE_FROWN
        }
    }

