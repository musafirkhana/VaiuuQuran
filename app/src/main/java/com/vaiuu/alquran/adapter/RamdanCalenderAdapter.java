package com.vaiuu.alquran.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vaiuu.alquran.main.R;
import com.vaiuu.alquran.util.Appconstant;


public class RamdanCalenderAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] names;

    static class ViewHolder {
        public TextView date;
        public TextView seheri;
        public TextView iftar;

    }

    public RamdanCalenderAdapter(Activity context, String[] names) {
        super(context, R.layout.row_cal, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_cal, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.date = (TextView) rowView.findViewById(R.id.date);
            viewHolder.seheri = (TextView) rowView.findViewById(R.id.seheri);
            viewHolder.iftar = (TextView) rowView.findViewById(R.id.iftar);


            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = names[position];
        holder.date.setText(Appconstant.date[position]);
        holder.seheri.setText(Appconstant.sahri[position]);
        holder.iftar.setText(Appconstant.iftar[position]);


        return rowView;
    }
}