package com.vaiuu.alquran.databse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	private int version = 1;
	private String databaseName;

	private Context mContext = null;

	private static DBManager dBManager = null;

	private DBManager(Context mContext) {
		super();
		this.mContext = mContext;

	}

	public static DBManager getInstance(Context mContext, String databaseName) {
		if (null == dBManager) {
			dBManager = new DBManager(mContext);
		}
		dBManager.databaseName = databaseName;
		return dBManager;
	}

	public void closeDatabase(SQLiteDatabase dataBase, Cursor cursor) {
		if (null != dataBase) {
			dataBase.close();
		}
		if (null != cursor) {
			cursor.close();
		}
	}

	public SQLiteDatabase openDatabase() {
		return getDatabaseHelper().getWritableDatabase();
	}

	public DataBaseHelper getDatabaseHelper() {
//		return new DataBaseHelper(mContext, this.databaseName, null,
//				this.version);
		return new DataBaseHelper(mContext);
	}

}
