package com.example.keke.babyface.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.keke.babyface.Data.ImageAssets;
import com.example.keke.babyface.R;

public class MainActivity2 extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    //Variables to store the values for the list index of the selected images
    //The default value will be index = 0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    //Track whether to display a two-pane or single-pane UI
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Determine whether two-pane or one-pane
        if (findViewById(R.id.babyface_linear_layout) != null) {
            //This layout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            //Change the GridView to space out the images more on tabley
            GridView gridView =  findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            //Getting rid of the "Next" Button
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            if (savedInstanceState == null) {
                //In two-pane mode, add initial BodyPartFragments to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();

                //Create a new head fragment
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setImageIds(ImageAssets.getHeads());
                //Add the fragment to its container using a transaction
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                //New body fragment
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(ImageAssets.getBodies());
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                //New leg fragment
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setImageIds(ImageAssets.getLegs());
                fragmentManager.beginTransaction()
                               .add(R.id.leg_container, legFragment)
                               .commit();
            }
        } else {
            //Single-pane mode and display fragments on a phone in separate activity
            mTwoPane = false;
        }
    }

    //Define the behavior for onImageSelected
    @Override
    public void onImageSelected(int position) {
        //Create a Toast that displays the position that was clicked
        Toast.makeText(this, "You clicked  " + position, Toast.LENGTH_SHORT).show();

        //Based on where a user has clicked, store the selected list index for the head, body, and leg
        int bodyPartNumber = position / 9;

        //Store the correct list index no matter where in the image list has been clicked
        int listIndex = position - 9 * bodyPartNumber;

        //Handle the two-pane case and replace existing fragments when a new img is selected from master list
        if (mTwoPane) {

            //Create two-pane interaction
            BodyPartFragment newFragment = new BodyPartFragment();

            //Set the  currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    newFragment.setImageIds(ImageAssets.getHeads());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImageIds(ImageAssets.getBodies());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImageIds(ImageAssets.getLegs());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        }else {

            //Handle the one-pane phone case by passing info in a bundle attached to an Intent

            //Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }

            //Put this information in a Bundle and attach it to an Intent
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            //Attach the Bundle to an intent
            final Intent intent = new Intent(this, MainActivity.class);
            intent.putExtras(b);

            //The Next button launches a new activity
            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }
}