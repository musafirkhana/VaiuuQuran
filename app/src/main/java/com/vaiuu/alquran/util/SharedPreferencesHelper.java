/*
 * Copyright (C) 2010 Mathieu Favez - http://mfavez.com
 *
 *
 * This file is part of FeedGoal.
 * 
 * FeedGoal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeedGoal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FeedGoal.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.vaiuu.alquran.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public final class SharedPreferencesHelper {

	private static final String LOG_TAG = "SharedPreferencesHelper";

	// Dialogs Id
	public static final int DIALOG_ABOUT = 0;
	public static final int DIALOG_NO_CONNECTION = 1;
	public static final int DIALOG_UPDATE_PROGRESS = 2;

	// Menu Groups Id
	public static final int CHANNEL_SUBMENU_GROUP = 0;
	public static final String REMEMBER = "remember";

	// App Preferences
	private static final String PREFS_FILE_NAME = "AppPreferences";
	private static final String PREF_TAB_FEED_KEY = "tabFeed";

	private static final String USER = "user";
	private static final String JSONDATA = "JSONDATA";

	private static final String PASS = "pass";

	private static final String RADIUS = "radius";
	private static final String ZOOM = "zoom";

	private static String ONLINE = "online";

	public static final String NULL = "";

	private static final String STATATIONURL = "stationurl";
	private static final String STATAIONTYPE = "stationtype";
	private static final String FIRSTTime = "FIRSTTime";

	public static final String SETCOUNT = "";

	public static boolean getFirstTime(final Context ctx) {
		return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(FIRSTTime, true);
	}

	public static void setFirstTime(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(FIRSTTime, flag);
		editor.commit();
	}

	public static String getFlurryAnalyticsApiKey(final Context ctx) {
		String flurryAnalyticsApiKey = null;
		try {
			final ApplicationInfo ai = ctx.getPackageManager()
					.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA);
			flurryAnalyticsApiKey = ai.metaData.getString("FLURRY_API_KEY");
		} catch (final NameNotFoundException nnfe) {
			Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
		}
		return flurryAnalyticsApiKey;
	}

	public static String getGoogleAnalyticsProfileId(final Context ctx) {
		String googleAnalyticsProfileId = null;
		try {
			final ApplicationInfo ai = ctx.getPackageManager()
					.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA);
			googleAnalyticsProfileId = ai.metaData
					.getString("GOOGLE_ANALYTICS_PROFILE_ID");
		} catch (final NameNotFoundException nnfe) {
			Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
		}
		return googleAnalyticsProfileId;
	}

	public static String getMobclixApplicationId(final Context ctx) {
		String mobclixApplicationId = null;
		try {
			final ApplicationInfo ai = ctx.getPackageManager()
					.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA);
			mobclixApplicationId = ai.metaData
					.getString("com.mobclix.APPLICATION_ID");
		} catch (final NameNotFoundException nnfe) {
			Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
		}
		return mobclixApplicationId;
	}

	public static String getPass(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.PASS, NULL);
	}

	public static String getJSONDATA(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.JSONDATA, NULL);
	}

	public static long getPrefTabFeedId(final Context ctx,
										final long defaultTabFeedId) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getLong(SharedPreferencesHelper.PREF_TAB_FEED_KEY,
						defaultTabFeedId);
	}

	public static boolean getRemember(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.REMEMBER, false);
	}

	public static int getSplashDuration(final Context ctx) {
		int splashScreenDuration = 2000;
		try {
			final ApplicationInfo ai = ctx.getPackageManager()
					.getApplicationInfo(ctx.getPackageName(),
							PackageManager.GET_META_DATA);
			splashScreenDuration = ai.metaData.getInt("splash_screen_duration");
		} catch (final NameNotFoundException nnfe) {
			Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
		}
		return splashScreenDuration;
	}

	public static String getUser(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.USER, "");
	}

	public static String getRadius(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.RADIUS, "3KM");
	}

	public static String getZoom(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.ZOOM, "11");
	}

	public static CharSequence getVersionName(final Context ctx) {
		CharSequence version_name = "";
		try {
			final PackageInfo packageInfo = ctx.getPackageManager()
					.getPackageInfo(ctx.getPackageName(), 0);
			version_name = packageInfo.versionName;
		} catch (final NameNotFoundException nnfe) {
			Log.e(SharedPreferencesHelper.LOG_TAG, "", nnfe);
		}
		return version_name;
	}

	public static boolean isOnline(final Context ctx) {
		final ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			return ni.isConnectedOrConnecting();
		} else {
			return false;
		}
	}

	public static void setPass(final Context ctx, final String pass) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.PASS, pass);
		editor.commit();
	}

	public static void setPrefTabFeedId(final Context ctx, final long feedId) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putLong(SharedPreferencesHelper.PREF_TAB_FEED_KEY, feedId);
		editor.commit();
	}

	public static void setRemember(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.REMEMBER, flag);
		editor.commit();
	}

	public static void setUser(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.USER, user);
		editor.commit();
	}

	public static void setJSONDATA(final Context ctx, final String data) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.JSONDATA, data);
		editor.commit();
	}

	public static void setRadius(final Context ctx, final String rad) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.RADIUS, rad);
		editor.commit();
	}

	public static void setZoom(final Context ctx, final String zoom) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.ZOOM, zoom);
		editor.commit();
	}

	public static boolean trackFlurryAnalytics(final Context ctx) {
		boolean track = true;
		final String flurryAnalyticsApiKey = SharedPreferencesHelper
				.getFlurryAnalyticsApiKey(ctx);

		if (flurryAnalyticsApiKey == null
				|| flurryAnalyticsApiKey
				.equalsIgnoreCase("xxxxxxxxxxxxxxxxxxxx")) {
			track = false;
		} else {
			track = true;
		}

		return track;
	}

	public static String getStationUrl(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.STATATIONURL, "");
	}

	public static String getStationType(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.STATAIONTYPE, "");
	}

	public static void setStationUrl(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.STATATIONURL, user);
		editor.commit();
	}

	public static void setStataionType(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.STATAIONTYPE, user);
		editor.commit();
	}

	// Shared getter util methods

	public static boolean trackGoogleAnalytics(final Context ctx) {
		boolean track = true;
		final String googleAnalyticsProfileId = SharedPreferencesHelper
				.getGoogleAnalyticsProfileId(ctx);

		if (googleAnalyticsProfileId == null
				|| googleAnalyticsProfileId.equalsIgnoreCase("UA-xxxxx-xx")) {
			track = false;
		} else {
			track = true;
		}

		return track;
	}

	public static boolean trackMobclixSession(final Context ctx) {
		boolean track = true;
		final String mobclixApplicationId = SharedPreferencesHelper
				.getMobclixApplicationId(ctx);

		if (mobclixApplicationId == null
				|| mobclixApplicationId
				.equalsIgnoreCase("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")) {
			track = false;
		} else {
			track = true;
		}

		return track;
	}

	public static void setOnlineOrOffline(final Context ctx, final boolean flag) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putBoolean(SharedPreferencesHelper.ONLINE, flag);
		editor.commit();
	}

	public static boolean getOnlineOrOffline(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.PREFS_FILE_NAME, Context.MODE_PRIVATE)
				.getBoolean(SharedPreferencesHelper.ONLINE, false);
	}



//Set Tasbih Count

	public static void setCount(final Context ctx, final String user) {
		final SharedPreferences prefs = ctx.getSharedPreferences(
				SharedPreferencesHelper.SETCOUNT, Context.MODE_PRIVATE);
		final Editor editor = prefs.edit();
		editor.putString(SharedPreferencesHelper.SETCOUNT, user);
		editor.commit();
	}

	public static String getCount(final Context ctx) {
		return ctx.getSharedPreferences(
				SharedPreferencesHelper.SETCOUNT, Context.MODE_PRIVATE)
				.getString(SharedPreferencesHelper.SETCOUNT, "");
	}
}
