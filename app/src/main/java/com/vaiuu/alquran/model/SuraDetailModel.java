package com.vaiuu.alquran.model;

public class SuraDetailModel {

	private String engText;
	private String arabicText;

	public String getBngText() {
		return bngText;
	}

	public void setBngText(String bngText) {
		this.bngText = bngText;
	}

	private String bngText;


	private boolean isRead=false;
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public String getEngText() {
		return engText;
	}
	public void setEngText(String engText) {
		this.engText = engText;
	}
	public String getArabicText() {
		return arabicText;
	}
	public void setArabicText(String arabicText) {
		this.arabicText = arabicText;
	}
	@Override
	public String toString() {
		return "SuraDetailModel [engText=" + engText + ", arabicText="
				+ arabicText + "]";
	}


	
	
	
	

}
