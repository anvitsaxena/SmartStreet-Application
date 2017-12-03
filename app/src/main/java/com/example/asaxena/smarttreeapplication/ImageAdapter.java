package com.example.asaxena.smarttreeapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by asaxena on 9/18/2017.
 */


public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    public ImageAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.mobile, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(mobileValues[position]);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            String mobile = mobileValues[position];

            if (mobile.equals("About")) {
                imageView.setImageResource(R.drawable.about_us);
            } else if (mobile.equals("Photo/Video")) {
                imageView.setImageResource(R.drawable.photo);
            } else if (mobile.equals("Interact")) {
                imageView.setImageResource(R.drawable.interact);
            } else if (mobile.equals("Share")) {
                imageView.setImageResource(R.drawable.share);
            } else if (mobile.equals("Comment")) {
                imageView.setImageResource(R.drawable.comment);
            } else if (mobile.equals("Add User Profile")) {
                imageView.setImageResource(R.drawable.adduser);
            } else if (mobile.equals("Nearby")) {
                imageView.setImageResource(R.drawable.nearby);
            }
            else{

            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
