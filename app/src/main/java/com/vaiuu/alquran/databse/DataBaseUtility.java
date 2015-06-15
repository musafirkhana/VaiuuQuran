package com.vaiuu.alquran.databse;

public class DataBaseUtility {

	/**
	 * Getting All Contact from DB
	 */
	/*public void getContactData(Context context ){
		AssetDatabaseOpenHelper databaseOpenHelper = new AssetDatabaseOpenHelper(context);
		SQLiteDatabase db = databaseOpenHelper.openDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT  Name, JobRole, JobRoleDescription, Photo, DisplayOnSite, DisplayOrder, Phone1 from  Personnel WHERE (DisplayOnSite = 1)"
                +"ORDER BY DisplayOrder LIMIT 7;",
				null);
		Log.w("Contact Data Data", "cnt: " + cursor.getCount());
		AllContactListVector contactListVector = new AllContactListVector();
		contactListVector.removeContactlist();
		if (cursor.moveToFirst()) {
			do {
				ContactListModel contactListModel = new ContactListModel();
				contactListModel.setName(cursor.getString(0));
				contactListModel.setJobRole(cursor.getString(1));
				contactListModel.setJobRoleDescription(cursor.getString(2));
				contactListModel.setPhoto(cursor.getString(3));
				contactListModel.setDisplayOnSite(cursor.getString(4));
				contactListModel.setDisplayOrder(cursor.getString(5));
				contactListModel.setPhone1(cursor.getString(6));
				contactListVector.setAllContactlist(contactListModel);
				contactListModel = null;
				Log.w("Contact Data Data", "cnt: " + cursor.getString(0));
			} while (cursor.moveToNext());
		}
		db.close();
	}
	public void getManagerContactData(Context context,String propertyID ){
		AssetDatabaseOpenHelper databaseOpenHelper = new AssetDatabaseOpenHelper(context);
		SQLiteDatabase db = databaseOpenHelper.openDatabase();
		Log.w("propertyID Data", "cnt: " + propertyID);
		Cursor cursor = db.rawQuery(
				"SELECT     Property.PropertyID, Personnel.Name, Personnel.Phone1, Personnel.Photo," +
				" Personnel.JobRole, Personnel.JobRoleDescription FROM   Personnel INNER JOIN Property ON Personnel.PersonnelID = Property.PersonnelID" +
				" WHERE     (Property.PropertyID = "+propertyID+");",
				null);
		Log.w("getManagerContactData Data", "cnt: " + cursor.getCount());
		AllManagerContactListVector managerContactListVector = new AllManagerContactListVector();
		managerContactListVector.removeManagerContactlist();
		if (cursor.moveToFirst()) {
			do {
				ManagerContactListModel managerContactListModel = new ManagerContactListModel();
				managerContactListModel.setPropertyID(cursor.getString(0));
				managerContactListModel.setName(cursor.getString(1));
				managerContactListModel.setPhone1(cursor.getString(2));
				managerContactListModel.setPhoto(cursor.getString(3));
				managerContactListModel.setJobRole(cursor.getString(4));
				managerContactListModel.setJobRoleDescription(cursor.getString(5));
				managerContactListVector.setAllManagerContactlist(managerContactListModel);
				managerContactListModel = null;
			} while (cursor.moveToNext());
		}
		db.close();
	}*/
}
