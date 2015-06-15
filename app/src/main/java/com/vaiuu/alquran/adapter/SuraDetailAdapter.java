package com.vaiuu.alquran.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vaiuu.alquran.holder.AllSuraDetailList;
import com.vaiuu.alquran.main.R;
import com.vaiuu.alquran.model.SuraDetailModel;

import java.util.Vector;

public class SuraDetailAdapter extends ArrayAdapter<SuraDetailModel> {
	Context context;

	public String response;
	public boolean asyncCheck = false;
	public String ContentCode;
	public String mobileNo;
	Vector<SuraDetailModel> temp;
	public SuraDetailAdapter(Context context,Vector<SuraDetailModel> suraDetailModels) {
		super(context, R.layout.row_suradetail, suraDetailModels);
		this.context = context;
		temp = AllSuraDetailList.getAllSuraDetailList();
		
	}

	static class ViewHolder {
	
		TextView sura_arabic_txt;
		TextView sura_eng_text;
		
	}

	Bitmap Shrinkmethod(String file, int width, int height) {
		BitmapFactory.Options bitopt = new BitmapFactory.Options();
		bitopt.inJustDecodeBounds = true;
		Bitmap bit = BitmapFactory.decodeFile(file, bitopt);

		int h = (int) Math.ceil(bitopt.outHeight / (float) height);
		int w = (int) Math.ceil(bitopt.outWidth / (float) width);

		if (h > 1 || w > 1) {
			if (h > w) {
				bitopt.inSampleSize = h;

			} else {
				bitopt.inSampleSize = w;
			}
		}
		bitopt.inJustDecodeBounds = false;
		bit = BitmapFactory.decodeFile(file, bitopt);

		return bit;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View v = convertView;

		if (v == null) {
			final LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_suradetail, null);
			// convertView = _inflater.inflate(R.layout.list_row, null);
			holder = new ViewHolder();
			
			holder.sura_arabic_txt=(TextView)v.findViewById(R.id.sura_arabic_txt);
			holder.sura_eng_text=(TextView)v.findViewById(R.id.sura_eng_text);
			
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		if (position < temp.size()) {
			final SuraDetailModel query = AllSuraDetailList.getAllSuraDetailList().elementAt(
					position);
			

			holder.sura_arabic_txt.setText(query.getArabicText());
			holder.sura_eng_text.setText(query.getBngText());
			if(query.isRead()){
				holder.sura_eng_text.setSelected(true);
				holder.sura_arabic_txt.setSelected(true);
			}else {
				holder.sura_eng_text.setSelected(false);
				holder.sura_arabic_txt.setSelected(false);
			}

		}

		return v;
	}



}