package com.vaiuu.alquran.holder;

import com.vaiuu.alquran.model.SuraDetailModel;

import java.util.Vector;


public class AllSuraDetailList {

	public static Vector<SuraDetailModel> suraDetailModels = new Vector<SuraDetailModel>();


	public static Vector<SuraDetailModel> getAllSuraDetailList() {
		return suraDetailModels;
	}

	public static void setAllSuraDetailList(Vector<SuraDetailModel> suraDetailModels) {
		AllSuraDetailList.suraDetailModels = suraDetailModels;
	}

	public static SuraDetailModel getSuraDetailList(int pos) {
		return suraDetailModels.elementAt(pos);
	}

	public static void setSuraDetailList(SuraDetailModel suraDetailModels) {
		AllSuraDetailList.suraDetailModels.addElement(suraDetailModels);
	}

	public static void removeSuraDetailList() {
		AllSuraDetailList.suraDetailModels.removeAllElements();
	}

}
