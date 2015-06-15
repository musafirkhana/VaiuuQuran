package com.vaiuu.alquran.model;

public class SuraListModel {

	private String id;
	private String engName;
	private String engArbName;
	private String arabicName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getEngArbName() {
		return engArbName;
	}
	public void setEngArbName(String engArbName) {
		this.engArbName = engArbName;
	}
	public String getArabicName() {
		return arabicName;
	}
	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}
	@Override
	public String toString() {
		return "SuraListModel [id=" + id + ", engName=" + engName
				+ ", engArbName=" + engArbName + ", arabicName=" + arabicName
				+ "]";
	}

	
	
	
	

}
