package com.vaiuu.alquran.parser;

import android.content.Context;

import com.vaiuu.alquran.holder.AllSuraList;
import com.vaiuu.alquran.model.SuraListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class SuraListParser {

	public static boolean connect(Context con, String result)
			throws JSONException, IOException {

		AllSuraList.removeSuraList();;
		if (result.length() < 1) {
			return false;

		}

		// final JSONObject mainJsonObject = new JSONObject(result);
		final JSONArray top_list = new JSONArray(result);
		SuraListModel suraListModel;
		for (int i = 0; i < top_list.length(); i++) {
			JSONObject top_list_jsonObject = top_list.getJSONObject(i);
			suraListModel = new SuraListModel();
			AllSuraList allSuraList = new AllSuraList();
			suraListModel.setId(top_list_jsonObject.getString("FIELD1"));
			suraListModel.setEngName(top_list_jsonObject.getString("FIELD2"));
			suraListModel.setEngArbName(top_list_jsonObject.getString("FIELD3"));
			suraListModel.setArabicName(top_list_jsonObject.getString("FIELD4"));
			
			allSuraList.setSuraList(suraListModel);;
			suraListModel = null;
		}

		return true;
	}
}
