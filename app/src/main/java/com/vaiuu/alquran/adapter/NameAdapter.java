package com.vaiuu.alquran.adapter;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vaiuu.alquran.main.R;


public class NameAdapter extends ArrayAdapter<String> {
    private final Activity context;
    public String namearabic[];
    public String namebangle[];
    public String nameeng[];

    static class ViewHolder {
        public TextView arabic;
        public TextView bangla;
        public TextView english;

    }

    public NameAdapter(Activity context,String[] namearabic,String[] namebangle,String[] nameeng) {
        super(context, R.layout.row_name,namearabic);
        this.context = context;
        this.namearabic = namearabic;
        this.namebangle = namebangle;
        this.nameeng = nameeng;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_name, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.arabic = (TextView) rowView.findViewById(R.id.arabic);
            viewHolder.bangla = (TextView) rowView.findViewById(R.id.bangla);
            viewHolder.english = (TextView) rowView.findViewById(R.id.english);


            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Log.i("Size of tion list",""+namearabic[position]);
        holder.arabic.setText(namearabic[position]);
        holder.bangla.setText(namebangle[position]);
        holder.english.setText(nameeng[position]);


        return rowView;
    }
}