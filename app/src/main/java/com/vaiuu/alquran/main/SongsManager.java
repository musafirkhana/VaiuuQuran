package com.vaiuu.alquran.main;

import android.util.Log;

import com.vaiuu.alquran.util.Appconstant;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
	// SDCard Path
	String MEDIA_PATH = "";
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	
	// Constructor
	public SongsManager(){
		
	}
	File  pathName = new File(
			android.os.Environment.getExternalStorageDirectory()
					+ File.separator + Appconstant.folderName);
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getPlayList(String folderName){
		MEDIA_PATH = new String("/sdcard/"+Appconstant.folderName+folderName);
		Log.i("MEDIA_PATH", MEDIA_PATH);
		File home = new File(MEDIA_PATH);

		if(!pathName.exists()){
//			AlertMessage.showMessage(, "Alert.....", "Sorry There are no downloaded Sura to play Please Download it First...Thank you for with us");
		}else 
		if (home.listFiles(new FileExtensionFilter()).length > 0) {
			for (File file : home.listFiles(new FileExtensionFilter())) {
				HashMap<String, String> song = new HashMap<String, String>();
				song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
				song.put("songPath", file.getPath());
				
				// Adding each song to SongList
				songsList.add(song);
			}
		}
		// return songs list array
		return songsList;
	}
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
}
