package com.example.keke.babyface.UI;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.keke.babyface.Data.ImageAssets;
import com.example.keke.babyface.R;

public class MasterListFragment extends Fragment {

    //Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnImageClickListener mCallback;

    //OnImageClickListener interface calls a method in the host activity named onImageSelected
    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    //Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //To make sure the host activity has implemented the callback interface
        //if not, it throws an exception

        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            + " must implement OnImageClickListener");
        }
    }


    //Mandatory empty constructor
    public MasterListFragment(){
    }

    //Inflates the GridView of all images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.fragment_matser_list, container, false);

        //Get a reference to the GridView in the layout file
        GridView gridView = rootView.findViewById(R.id.images_grid_view);

        //Create the adapter
        MasterListAdapter mAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mAdapter = new MasterListAdapter(getContext(), ImageAssets.getAll());
        }

        //Set the adapter on the GridView
        gridView.setAdapter(mAdapter);

        //Set a click listener on the gridView and trigger the callback onImageSelected when an item is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
              //Trigger the callback method and pass in the position that was clicked
              mCallback.onImageSelected(position);
          }
        });

        //Return the root view
        return rootView;
    }
}
