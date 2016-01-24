package com.vaiuu.alquran.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vaiuu.alquran.adapter.SuraDetailAdapter;
import com.vaiuu.alquran.adapter.SuraListAdapter;
import com.vaiuu.alquran.holder.AllSuraDetailList;
import com.vaiuu.alquran.holder.AllSuraList;
import com.vaiuu.alquran.model.SuraListModel;
import com.vaiuu.alquran.util.Appconstant;
import com.vaiuu.alquran.util.HTTPHandler;
import com.vaiuu.alquran.util.HTTPPostHelper;
import com.vaiuu.alquran.util.SharedPreferencesHelper;
import com.vaiuu.alquran.util.UnzipUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class QuranPlayerActivity extends ActionBarActivity implements OnClickListener,
		OnCompletionListener {

	private Context context;
	private ListView sura_detaillistview;
	SuraListAdapter suraListAdapter;
	private ProgressDialog progressDialog;
	private ImageButton btnPlay;
	private ImageButton btnPrevious;
	private ImageButton btnNext;
	private ImageButton btnShuffle;
	private ImageButton btnRepeat;
	private MediaPlayer mp;
	private int currentSongIndex = 0;
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	private TextView tvSettingTitle;

	private SuraDetailAdapter suraDetailAdapter;
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private SongsManager songManager;
	File pathName = null;
	
	
	private String response;
	public DownloadZipfile mew = new DownloadZipfile();
	private String downloadUrl="";
	
	public String unzipLocation = Environment.getExternalStorageDirectory()+ Appconstant.DB_BASE_URL;
	public String StorezipFileLocation = Environment.getExternalStorageDirectory()+ "/DownloadedZip";
	public String DirectoryName = Environment.getExternalStorageDirectory()+ Appconstant.DB_BASE_URL;

	private Toolbar mToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quran_player);
		context = this;

		mToolbar = (Toolbar) findViewById(R.id.toolbar_musjid);
		setSupportActionBar(mToolbar);
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle("");
		downloadUrl="http://www.vaiuugroupbd.org/alquran/reading/"
				+ ""+Appconstant.suraPosition
				+ ".zip";
		progressDialog = new ProgressDialog(context);
		songManager = new SongsManager();
		// Important

		
		pathName = new File(Environment.getExternalStorageDirectory()
						+ File.separator + Appconstant.folderName + ""
						+ Appconstant.suraPosition);

		initUI();

	}

	private void initUI() {


		Log.i("Element Position ", ""
				+ AllSuraList.getAllSuraList().size());
		final SuraListModel query = AllSuraList.getAllSuraList().elementAt(Appconstant.suraPosition-1);
		tvSettingTitle = (TextView) findViewById(R.id.tvSettingTitle);
		tvSettingTitle.setText(query.getEngName());
		Log.i("Last Index", ""+AllSuraList.getAllSuraList().lastElement());
		sura_detaillistview = (ListView) findViewById(R.id.sura_detaillistview);
		sura_detaillistview.setSelection(2);
		sura_detaillistview.setSelected(true);
		sura_detaillistview.requestFocus();
		suraDetailAdapter = new SuraDetailAdapter(context,
				AllSuraDetailList.getAllSuraDetailList());
		sura_detaillistview.setAdapter(suraDetailAdapter);
		suraDetailAdapter.notifyDataSetChanged();

		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
		btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
		btnPlay.setOnClickListener(this);
		btnPrevious.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnRepeat.setOnClickListener(this);
		btnShuffle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnPlay:
			//if(Appconstant.suraPosition<50){
				playPouse();
//			}else {
//				Toast.makeText(context, "This Audio Is Not Available", Toast.LENGTH_SHORT)
//				.show();
//			}
		

			break;

		case R.id.btnPrevious:
			if (mp != null) {
//				if(Appconstant.suraPosition>50)
//				{
//					Toast.makeText(context, "This Audio Is Not Available", Toast.LENGTH_SHORT)
//					.show();
//					return;
//				}
				AllSuraDetailList.getAllSuraDetailList()
				.elementAt(currentSongIndex).setRead(false);
				
				
				if (currentSongIndex > 0) {
					playSong(currentSongIndex - 1);
					currentSongIndex = currentSongIndex - 1;
				} else {
					// play last song
					playSong(songsList.size() - 1);
					currentSongIndex = songsList.size() - 1;
				}
				AllSuraDetailList.getAllSuraDetailList()
						.elementAt(currentSongIndex).setRead(true);
				sura_detaillistview.setSelection(currentSongIndex);
				sura_detaillistview.smoothScrollToPosition(currentSongIndex);
				suraDetailAdapter.notifyDataSetChanged();
			}
			

			break;
		case R.id.btnNext:
			if (mp != null) {
//				if(Appconstant.suraPosition>50)
//				{
//					Toast.makeText(context, "This Audio Is Not Available", Toast.LENGTH_SHORT)
//					.show();
//					return;
//				}
				// check if next song is there or not
				AllSuraDetailList.getAllSuraDetailList()
				.elementAt(currentSongIndex).setRead(false);
				
				if (currentSongIndex < (songsList.size() - 1)) {
					playSong(currentSongIndex + 1);
					currentSongIndex = currentSongIndex + 1;
				} else {
					// play first song
					playSong(0);
					currentSongIndex = 0;
				}
				AllSuraDetailList.getAllSuraDetailList()
				.elementAt(currentSongIndex).setRead(true);
				sura_detaillistview.setSelection(currentSongIndex);
				sura_detaillistview.smoothScrollToPosition(currentSongIndex);
				suraDetailAdapter.notifyDataSetChanged();
			}
			

			break;

		case R.id.btnRepeat:
			if (isRepeat) {
				isRepeat = false;
				Toast.makeText(getApplicationContext(), "Repeat is OFF",
						Toast.LENGTH_SHORT).show();
				btnRepeat.setImageResource(R.drawable.btn_repeat);
			} else {
				// make repeat to true
				isRepeat = true;
				Toast.makeText(getApplicationContext(), "Repeat is ON",
						Toast.LENGTH_SHORT).show();
				// make shuffle to false
				isShuffle = false;
				btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
				btnShuffle.setImageResource(R.drawable.btn_shuffle);
			}
			break;

		case R.id.btnShuffle:
			if (isShuffle) {
				isShuffle = false;
				Toast.makeText(getApplicationContext(), "Shuffle is OFF",
						Toast.LENGTH_SHORT).show();
				btnShuffle.setImageResource(R.drawable.btn_shuffle);
			} else {
				// make repeat to true
				isShuffle = true;
				Toast.makeText(getApplicationContext(), "Shuffle is ON",
						Toast.LENGTH_SHORT).show();
				// make shuffle to false
				isRepeat = false;
				btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
				btnRepeat.setImageResource(R.drawable.btn_repeat);
			}

			break;
		}
	}

	
	private void playPouse(){
		if (pathName.exists()) {
			// check for already playing
			// Getting all songs list
			songsList = songManager.getPlayList(""+Appconstant.suraPosition);
			if (mp != null) {
				if (mp.isPlaying()) {

					mp.pause();
					// Changing button image to play button
					btnPlay.setImageResource(R.drawable.btn_play);

				} else {
					mp.start();
					// Changing button image to pause button
					btnPlay.setImageResource(R.drawable.btn_pause);
				}
			} else {
				currentSongIndex = 0;
				mp = new MediaPlayer();
				mp.setOnCompletionListener(this);
				AllSuraDetailList.getAllSuraDetailList().elementAt(0)
						.setRead(true);
				playSong(0);

				btnPlay.setImageResource(R.drawable.btn_pause);
			}

		} else {
			
			
			mew = new DownloadZipfile();
			mew.execute(downloadUrl);
//			Toast.makeText(context, "Please Download", Toast.LENGTH_SHORT)
//					.show();
		}
	}
	/**
	 * Function to play a song
	 * 
	 * @param songIndex
	 *            - index of song
	 * */
	public void playSong(int songIndex) {
		// Play song
		try {

			mp.reset();
			mp.setDataSource(songsList.get(songIndex).get("songPath"));
			mp.prepare();
			mp.start();
			
			// Changing Button Image to pause image
			btnPlay.setImageResource(R.drawable.btn_pause);

			suraDetailAdapter.notifyDataSetChanged();
			// set Progress bar values
			// songProgressBar.setProgress(0);
			// songProgressBar.setMax(100);

			// Updating progress bar
			// updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * On Song Playing completed if repeat is ON play same song again if shuffle
	 * is ON play random song
	 * */
	public void onCompletion(MediaPlayer arg0) {

		// check for repeat is ON or OFF
		if (isRepeat) {
			// repeat is on play same song again
			playSong(currentSongIndex);
		} else if (isShuffle) {
			// shuffle is on - play a random song
			Random rand = new Random();
			currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
			playSong(currentSongIndex);
		} else {
			// no repeat or shuffle ON - play next song
			if (currentSongIndex < (songsList.size() - 1)) {
				try {
					AllSuraDetailList.getAllSuraDetailList()
							.elementAt(currentSongIndex).setRead(false);

				} catch (Exception e) {
					// TODO: handle exception
				}

				playSong(currentSongIndex + 1);

				currentSongIndex = currentSongIndex + 1;
				try {
					int po = AllSuraDetailList.getAllSuraDetailList().size();

					if (po > currentSongIndex) {
						AllSuraDetailList.getAllSuraDetailList().elementAt(currentSongIndex).setRead(true);
						sura_detaillistview.setSelection(currentSongIndex);
						sura_detaillistview.smoothScrollToPosition(currentSongIndex);
						suraDetailAdapter.notifyDataSetChanged();
					} else {
						AllSuraDetailList.getAllSuraDetailList().elementAt(currentSongIndex - 1).setRead(true);
						sura_detaillistview.setSelection(currentSongIndex - 1);
						sura_detaillistview
								.smoothScrollToPosition(currentSongIndex - 1);
						suraDetailAdapter.notifyDataSetChanged();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				sura_detaillistview.setSelectionAfterHeaderView();

			} else {
				// play first song
				// playSong(0);
				// currentSongIndex = 0;

				mp.stop();
				mp = null;
				btnPlay.setImageResource(R.drawable.btn_play);
				int pos = AllSuraDetailList.getAllSuraDetailList().size() - 1;
				AllSuraDetailList.getAllSuraDetailList().elementAt(pos)
						.setRead(false);
				suraDetailAdapter.notifyDataSetChanged();
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			mp.release();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void BackBtn(View v) {
		finish();
	}
	
	
	
	
	
	
	/**
	 * Download AsyncTask Task this class Download a Zip File And Unzipp it
	 * @author Ali
	 *
	 **/

	class DownloadZipfile extends AsyncTask<String, String, String> {
		String result = "";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Downloading...Please Wait");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();
				int lenghtOfFile = conexion.getContentLength();
				InputStream input = new BufferedInputStream(url.openStream());

				OutputStream output = new FileOutputStream(StorezipFileLocation);

				byte data[] = new byte[1024];
				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}
				output.close();
				input.close();
				result = "true";

			} catch (Exception e) {

				result = "false";
			}
			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
			progressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			progressDialog.dismiss();
			if (result.equalsIgnoreCase("true")) {
				try {
					unzip();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

			}
		}
	}

	/**
	 * This is the method for unzip file which is store your location. And unzip
	 * folder will store as per your desire location.
	 * @throws IOException
	 **/

	public void unzip() throws IOException {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Please Wait...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.show();
		new UnZipTask().execute(StorezipFileLocation, DirectoryName);

	}

	private class UnZipTask extends AsyncTask<String, Void, Boolean> {
		@SuppressWarnings("rawtypes")
		@Override
		protected Boolean doInBackground(String... params) {
			String filePath = params[0];
			String destinationPath = params[1];

			File archive = new File(filePath);
			try {
				ZipFile zipfile = new ZipFile(archive);
				for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
					ZipEntry entry = (ZipEntry) e.nextElement();
					unzipEntry(zipfile, entry, destinationPath);
				}

				UnzipUtil d = new UnzipUtil(StorezipFileLocation, DirectoryName);
				d.unzip();

			} catch (Exception e) {
				return false;
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
			
			playPouse();
			

//			Intent intent = ((Activity) context).getIntent();
//			((Activity) context).finish();
//			((Activity) context).startActivity(intent);

		}

		private void unzipEntry(ZipFile zipfile, ZipEntry entry,
				String outputDir) throws IOException {

			if (entry.isDirectory()) {
				createDir(new File(outputDir, entry.getName()));
				return;
			}

			File outputFile = new File(outputDir, entry.getName());
			if (!outputFile.getParentFile().exists()) {
				createDir(outputFile.getParentFile());
			}

			BufferedInputStream inputStream = new BufferedInputStream(
					zipfile.getInputStream(entry));
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(outputFile));

			try {

			} finally {
				outputStream.flush();
				outputStream.close();
				inputStream.close();
			}
		}

		private void createDir(File dir) {
			if (dir.exists()) {
				return;
			}
			if (!dir.mkdirs()) {
				throw new RuntimeException("Can not create dir " + dir);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void downloadTask(final HTTPPostHelper mHTTPPostHelper) {

		if (!SharedPreferencesHelper.isOnline(context)) {

			
			return;
		}

		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Please Wait...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.show();

		(new AsyncTask() {

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();

			}

			@Override
			protected Object doInBackground(Object... params) {
				// TODO Auto-generated method stub

				try {

					response = HTTPHandler.GetPostDataFromURL(mHTTPPostHelper);
					Log.i("response", response);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				String country = "";
				if (progressDialog != null) {
					progressDialog.dismiss();;
				}
				if(response.equalsIgnoreCase("success"))
				{
					mew = new DownloadZipfile();
					mew.execute(downloadUrl);	
				}else {
					
				}
				

					return;
				

			}

		}).execute();
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			QuranPlayerActivity.this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
