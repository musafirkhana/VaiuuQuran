package com.vaiuu.alquran.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaiuu.alquran.main.R;


public class LeftDrawerAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] names;
    public static final Integer[] images = {R.drawable.ic_leftmenu,
            R.drawable.ic_leftmenu, R.drawable.ic_leftmenu, R.drawable.ic_leftmenu,
            R.drawable.ic_leftmenu, R.drawable.ic_leftmenu};

    static class ViewHolder {
        public ImageView left_image;
        public TextView left_text;

    }

    public LeftDrawerAdapter(Activity context, String[] names) {
        super(context, R.layout.row_drawer, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_drawer, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.left_text = (TextView) rowView.findViewById(R.id.left_text);
            viewHolder.left_image = (ImageView) rowView.findViewById(R.id.left_image);


            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = names[position];
        holder.left_text.setText(names[position]);
        holder.left_image.setImageResource(images[position]);

        return rowView;
    }
}