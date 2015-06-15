package com.vaiuu.alquran.databse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.vaiuu.alquran.util.Appconstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AssetDatabaseOpenHelper {

	private static final String DB_NAME = "quran.ar.db";
	private File sdCard = Environment.getExternalStorageDirectory();
	private static String DB_SD_CARD_PATH = "";

	private Context context;

	public AssetDatabaseOpenHelper(Context context) {
		this.context = context;
	}

	public SQLiteDatabase openDatabase() {
		DB_SD_CARD_PATH = sdCard.getAbsolutePath() + Appconstant.DB_BASE_URL;
		File dbFile = new File(DB_SD_CARD_PATH + DB_NAME);

//		if (!dbFile.exists()) {
//			try {
//				copyDatabase(dbFile);
//			} catch (IOException e) {
//				throw new RuntimeException("Error creating source database", e);
//			}
//		}

		return SQLiteDatabase.openDatabase(dbFile.getPath(), null,
				SQLiteDatabase.OPEN_READONLY);
	}

	private void copyDatabase(File dbFile) throws IOException {
		InputStream mInput = context.getAssets().open(DB_NAME);
		String outFileName = DB_SD_CARD_PATH + DB_NAME;
		OutputStream mOutput = new FileOutputStream(outFileName);
		byte[] mBuffer = new byte[1024];
		int mLength;
		while ((mLength = mInput.read(mBuffer)) > 0) {
			mOutput.write(mBuffer, 0, mLength);
		}
		mOutput.flush();
		mOutput.close();
		mInput.close();
	}

}
