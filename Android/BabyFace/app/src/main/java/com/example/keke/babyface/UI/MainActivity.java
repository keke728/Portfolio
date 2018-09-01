package com.example.keke.babyface.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.keke.babyface.Data.ImageAssets;
import com.example.keke.babyface.R;

//This activity will display a custom Android image composed of three body parts: head, body, and legs
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Only create new fragments when there is no previously saved state
        if(savedInstanceState == null) {

            //Create a new BodyPartFragment instance and display it using the FragmentManager
            BodyPartFragment headFragment = new BodyPartFragment();

            //Set the list of image ids for the head fragment and set the position to the second image in the list
            headFragment.setImageIds(ImageAssets.getHeads());

            //Get the correct index to access in the array of head images from the intent
            //Set the default value to 0
            int headIndex = getIntent().getIntExtra("headIndex", 0);
            headFragment.setmListIndex(headIndex);

            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            //Fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit();

            //Create and display the body and leg BodyPartFragments
            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setImageIds(ImageAssets.getBodies());
            int bodyIndex = getIntent().getIntExtra("bodyIndex",0);
            bodyFragment.setmListIndex(bodyIndex);

            fragmentManager.beginTransaction()
                           .add(R.id.body_container, bodyFragment)
                           .commit();

            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setImageIds(ImageAssets.getLegs());
            int legIndex = getIntent().getIntExtra("legIndex", 0);
            legFragment.setmListIndex(legIndex);

            fragmentManager.beginTransaction()
                           .add(R.id.leg_container, legFragment)
                           .commit();
        }
    }
}
