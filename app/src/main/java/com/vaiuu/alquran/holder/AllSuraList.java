package com.vaiuu.alquran.holder;

import com.vaiuu.alquran.model.SuraListModel;

import java.util.Vector;


public class AllSuraList {

	public static Vector<SuraListModel> suraListModels = new Vector<SuraListModel>();


	public static Vector<SuraListModel> getAllSuraList() {
		return suraListModels;
	}

	public static void setAllSuraList(Vector<SuraListModel> suraListModels) {
		AllSuraList.suraListModels = suraListModels;
	}

	public static SuraListModel getSuraList(int pos) {
		return suraListModels.elementAt(pos);
	}

	public static void setSuraList(SuraListModel suraListModels) {
		AllSuraList.suraListModels.addElement(suraListModels);
	}

	public static void removeSuraList() {
		AllSuraList.suraListModels.removeAllElements();
	}

}
