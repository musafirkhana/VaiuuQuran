package com.vaiuu.alquran.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaiuu.alquran.holder.AllSuraList;
import com.vaiuu.alquran.main.R;
import com.vaiuu.alquran.model.SuraListModel;
import com.vaiuu.alquran.util.Appconstant;

import java.io.File;


public class SuraListAdapter extends ArrayAdapter<SuraListModel> {
	Context context;

	public String response;
	public boolean asyncCheck = false;
	public String ContentCode;
	public String mobileNo;
	File pathName = null;

	public SuraListAdapter(Context context) {
		super(context, R.layout.row_sura, AllSuraList.getAllSuraList());
		this.context = context;
		
	}

	static class ViewHolder {
	
		TextView sura_eng;
		TextView sura_arabic;
		TextView sura_number;
		ImageView download_ok;
		
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
			v = vi.inflate(R.layout.row_sura, null);
			// convertView = _inflater.inflate(R.layout.list_row, null);
			holder = new ViewHolder();
			
			holder.sura_eng=(TextView)v.findViewById(R.id.sura_eng);
			holder.sura_arabic=(TextView)v.findViewById(R.id.sura_arabic);
			holder.sura_number=(TextView)v.findViewById(R.id.sura_number);
			holder.download_ok = (ImageView) v.findViewById(R.id.download_ok);
			
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		if (position < AllSuraList.getAllSuraList().size()) {
			final SuraListModel query = AllSuraList.getAllSuraList().elementAt(
					position);

			pathName = new File(
					android.os.Environment.getExternalStorageDirectory()
							+ File.separator + Appconstant.folderName + ""
							+ (position+1));
			if (pathName.exists()) {
				holder.download_ok.setVisibility(View.VISIBLE);
			} else {
				holder.download_ok.setVisibility(View.GONE);
			}
			holder.sura_eng.setText(query.getEngArbName());
			holder.sura_arabic.setText(query.getArabicName());
			holder.sura_number.setText(""+(position+1));

		}

		return v;
	}



}