package com.vaiuu.alquran.databse;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteTemplate {

	/**
	 * Default Primary key
	 */
	protected String mPrimaryKey = "_id";

	/**
	 * DBManager
	 */
	private DBManager dBManager;

	private boolean isTransaction = false;

	private SQLiteDatabase dataBase = null;

	private SQLiteTemplate() {
	}

	private SQLiteTemplate(DBManager dBManager, boolean isTransaction) {
		this.dBManager = dBManager;
		this.isTransaction = isTransaction;
	}

	public static SQLiteTemplate getInstance(DBManager dBManager,
			boolean isTransaction) {
		return new SQLiteTemplate(dBManager, isTransaction);
	}

	public void execSQL(String sql) {
		try {
			dataBase = dBManager.openDatabase();
			dataBase.execSQL(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
	}

	public void execSQL(String sql, Object[] bindArgs) {
		try {
			dataBase = dBManager.openDatabase();
			dataBase.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
	}

	public long insert(String table, ContentValues content) {
		try {

			Log.w("dBManager", "name" + dBManager);
			dataBase = dBManager.openDatabase();
			return dataBase.insert(table, null, content);
		} catch (Exception e) {
			Log.w("Exception", "dBManager" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return 0;
	}

	public void deleteByIds(String table, Object... primaryKeys) {
		try {
			if (primaryKeys.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (@SuppressWarnings("unused")
				Object id : primaryKeys) {
					sb.append("?").append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				dataBase = dBManager.openDatabase();
				dataBase.execSQL("delete from " + table + " where "
						+ mPrimaryKey + " in(" + sb + ")",
						(Object[]) primaryKeys);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
	}

	public int deleteByField(String table, String field, String value) {
		try {
			dataBase = dBManager.openDatabase();
			return dataBase.delete(table, field + "=?", new String[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return 0;
	}

	public int deleteByCondition(String table, String whereClause,
			String[] whereArgs) {
		try {
			dataBase = dBManager.openDatabase();
			return dataBase.delete(table, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return 0;
	}

	public int deleteById(String table, String id) {
		try {
			dataBase = dBManager.openDatabase();
			return deleteByField(table, mPrimaryKey, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return 0;
	}

	public int updateById(String table, String id, ContentValues values) {
		try {
			dataBase = dBManager.openDatabase();
			return dataBase.update(table, values, mPrimaryKey + "=?",
					new String[] { id });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return 0;
	}

	public int update(String table, ContentValues values, String whereClause,
			String[] whereArgs) {
		try {
			dataBase = dBManager.openDatabase();
			return dataBase.update(table, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return 0;
	}

	public Boolean isExistsById(String table, String id) {
		try {
			dataBase = dBManager.openDatabase();
			return isExistsByField(table, mPrimaryKey, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return null;
	}

	public Boolean isExistsByField(String table, String field, String value) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(*) FROM ").append(table).append(" WHERE ")
				.append(field).append(" =?");
		try {
			dataBase = dBManager.openDatabase();
			return isExistsBySQL(sql.toString(), new String[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(null);
			}
		}
		return null;
	}

	public Boolean isExistsBySQL(String sql, String[] selectionArgs) {
		Cursor cursor = null;
		try {
			dataBase = dBManager.openDatabase();
			cursor = dataBase.rawQuery(sql, selectionArgs);
			if (cursor.moveToFirst()) {
				return (cursor.getInt(0) > 0);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(cursor);
			}
		}
		return null;
	}

	public <T> T queryForObject(RowMapper<T> rowMapper, String sql,
			String[] args) {
		Cursor cursor = null;
		T object = null;
		try {
			dataBase = dBManager.openDatabase();
			cursor = dataBase.rawQuery(sql, args);
			if (cursor.moveToFirst()) {
				object = rowMapper.mapRow(cursor, cursor.getCount());
			}
		} finally {
			if (!isTransaction) {
				closeDatabase(cursor);
			}
		}
		return object;

	}

	public <T> List<T> queryForList(RowMapper<T> rowMapper, String sql,
			String[] selectionArgs) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			dataBase = dBManager.openDatabase();
			cursor = dataBase.rawQuery(sql, selectionArgs);
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
			}
		} finally {
			if (!isTransaction) {
				closeDatabase(cursor);
			}
		}
		return list;
	}

	public <T> List<T> queryForList(RowMapper<T> rowMapper, String sql,
			int startResult, int maxResult) {
		Cursor cursor = null;
		List<T> list = null;
		try {
			dataBase = dBManager.openDatabase();
			cursor = dataBase.rawQuery(sql + " limit ?,?", new String[] {
					String.valueOf(startResult), String.valueOf(maxResult) });
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
			}
		} finally {
			if (!isTransaction) {
				closeDatabase(cursor);
			}
		}
		return list;
	}

	public Integer getCount(String sql, String[] args) {
		Cursor cursor = null;
		try {
			dataBase = dBManager.openDatabase();
			cursor = dataBase.rawQuery("select count(*) from (" + sql + ")",
					args);
			if (cursor.moveToNext()) {
				return cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isTransaction) {
				closeDatabase(cursor);
			}
		}
		return 0;
	}

	public <T> List<T> queryForList(RowMapper<T> rowMapper, String table,
			String[] columns, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy, String limit) {
		List<T> list = null;
		Cursor cursor = null;
		try {
			dataBase = dBManager.openDatabase();
			cursor = dataBase.query(table, columns, selection, selectionArgs,
					groupBy, having, orderBy, limit);
			list = new ArrayList<T>();
			while (cursor.moveToNext()) {
				list.add(rowMapper.mapRow(cursor, cursor.getPosition()));
			}
		} finally {
			if (!isTransaction) {
				closeDatabase(cursor);
			}
		}
		return list;
	}

	/**
	 * Get Primary Key
	 * 
	 * @return
	 */
	public String getPrimaryKey() {
		return mPrimaryKey;
	}

	/**
	 * Set Primary Key
	 * 
	 * @param primaryKey
	 */
	public void setPrimaryKey(String primaryKey) {
		this.mPrimaryKey = primaryKey;
	}

	/**
	 * 
	 * @param <T>
	 */
	public interface RowMapper<T> {
		/**
		 * 
		 * @param cursor
		 * 
		 * @param index
		 * 
		 * @return
		 */
		public T mapRow(Cursor cursor, int index);
	}

	public void closeDatabase(Cursor cursor) {
		if (null != dataBase) {
			dataBase.close();
		}
		if (null != cursor) {
			cursor.close();
		}
	}
}
