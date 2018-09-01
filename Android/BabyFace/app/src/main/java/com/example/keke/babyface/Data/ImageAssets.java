package com.example.keke.babyface.Data;


import com.example.keke.babyface.R;

import java.util.ArrayList;
import java.util.List;

//Class for storing all the image drawable resources in ArrayLists
public class ImageAssets {

    //Lists for all project images
    //Broken down into heads, bodies, legs and all images

    private static final List<Integer> heads= new ArrayList<Integer>() {{
        add(R.drawable.head1);
        add(R.drawable.head2);
        add(R.drawable.head3);
        add(R.drawable.head4);
        add(R.drawable.head5);
        add(R.drawable.head6);
        add(R.drawable.head7);
        add(R.drawable.head8);
        add(R.drawable.head9);
    }};

    private static final List<Integer> bodies = new ArrayList<Integer>() {{
        add(R.drawable.body1);
        add(R.drawable.body2);
        add(R.drawable.body3);
        add(R.drawable.body4);
        add(R.drawable.body5);
        add(R.drawable.body6);
        add(R.drawable.body7);
        add(R.drawable.body8);
        add(R.drawable.body9);
    }};

    private static final List<Integer> legs = new ArrayList<Integer>() {{
        add(R.drawable.leg1);
        add(R.drawable.leg2);
        add(R.drawable.leg3);
        add(R.drawable.leg4);
        add(R.drawable.leg5);
        add(R.drawable.leg6);
        add(R.drawable.leg7);
        add(R.drawable.leg8);
        add(R.drawable.leg9);
    }};

    private static final List<Integer> all = new ArrayList<Integer>() {{
        addAll(heads);
        addAll(bodies);
        addAll(legs);
    }};


    //Getter methods that return lists of all all head images, body images, les images
    public static List<Integer> getHeads() {
        return heads;
    }

    public static List<Integer> getBodies() {
        return bodies;
    }

    public static List<Integer> getLegs() {
        return legs;
    }

    //Return a list of all the images combined: heads, bodies, and legs in that order
    public static List<Integer> getAll() {return all;}

}
