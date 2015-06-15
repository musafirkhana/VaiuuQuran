package com.vaiuu.alquran.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpRequest This class handles POST and GET requests and enables you to
 * upload files via post. Cookies are stored in the HttpClient.
 * 
 * @author Sander Borgman
 * @url http://www.sanderborgman.nl
 * @version 1
 */
public class HttpRequest {
	// public static String currentUserName = "";
	// public static String currentPass = "";
	// public static String currentVersion = "";
	// public static String currentURL = "";
	//
	// public static void resetAllUserData() {
	//
	// currentUserName = "";
	// currentPass = "";
	// currentVersion = "";
	// currentURL = "";
	//
	// }

	/*
	 * 
	 * do log in
	 */
	public static String doLogin(String url, final String username,
			final String pass) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", pass));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * notify list request
	 */
	public static String notificationList(String url, final String username) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("notify  URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		// nvps.add(new BasicNameValuePair("password", pass));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * notify delete request
	 */
	public static String notificationDeleteRequest(String url,
			final String username, final String asin) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("notify  URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("asin", asin));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * clear all notify delete request
	 */
	public static String clearAllNotificationRequest(String url,
			final String username) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("notify  URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		// nvps.add(new BasicNameValuePair("asin", asin));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * exact author suggesion
	 */
	public static String exactAuthorSuggession(String url,
			final String authorname) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("authorname", authorname));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * search for author
	 */
	public static String searchAuthor(String url, final String keyword) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("searchkeyword", keyword));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * update app id
	 */
	public static String updateAppID(String url, final String username,
			final String appid, final String emailid, final String countrycode,
			final String appversion) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("appid", appid));
		nvps.add(new BasicNameValuePair("email", emailid));
		nvps.add(new BasicNameValuePair("countrycode", countrycode));

		nvps.add(new BasicNameValuePair("appversion", appversion));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * delete MyBook
	 */
	public static String deleteMyBook(String url, final String username,
			final String asin) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("asin", asin));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * add MyBook
	 */
	public static String addMyBook(String url, final String username,
			final String asin, final String detailpageurl,
			final String binding, final String isbn, final String title,
			final String tobuy, final String authorid) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("asin", asin));
		nvps.add(new BasicNameValuePair("detailpageurl", detailpageurl));
		nvps.add(new BasicNameValuePair("binding", binding));
		nvps.add(new BasicNameValuePair("isbn", isbn));
		nvps.add(new BasicNameValuePair("title", title));
		nvps.add(new BasicNameValuePair("tobuy", tobuy));
		nvps.add(new BasicNameValuePair("favauthorid", authorid));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * password recover
	 */
	public static String passwordRecover(String url, final String useremail) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("email", useremail));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * do log in
	 */
	public static String getAuthorBookList(String url, final String authorname,
			final String alias, final String pagenumber, final String binding,
			final String localestr) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("authorfullname", authorname));
		nvps.add(new BasicNameValuePair("alias", alias));
		nvps.add(new BasicNameValuePair("pagenumber", pagenumber));
		nvps.add(new BasicNameValuePair("binding", binding));
		nvps.add(new BasicNameValuePair("countrycode", localestr));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * author suggession list
	 */
	public static String getAuthorSuggessionList(String url,
			final String authorname, final String alias,
			final String pagenumber, final String binding,
			final String localestr) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("authorfullname", authorname));
		nvps.add(new BasicNameValuePair("alias", alias));
		nvps.add(new BasicNameValuePair("pagenumber", pagenumber));
		nvps.add(new BasicNameValuePair("binding", binding));
		nvps.add(new BasicNameValuePair("countrycode", localestr));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * do log in
	 */
	public static String getMyLibraryBooks(String url, final String username) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		// nvps.add(new BasicNameValuePair("password", pass));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * deltobuy list
	 */
	public static String getDelTObuyLlist(String url, final String username) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		// nvps.add(new BasicNameValuePair("password", pass));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	/*
	 * 
	 * deltobuy list
	 */
	public static String deleteDelTObuy(String url, final String username,
			final String asin) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("asin", asin));
		// nvps.add(new BasicNameValuePair("password", pass));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	public static String getFavoriteAllList(String url, final String usename) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", usename));
		// nvps.add(new BasicNameValuePair("authorid", id));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	// public static String getPromoList(String url) {
	//
	// // currentUserName = username;
	// // currentPass = pass;
	//
	// final HttpPost httpost = new HttpPost(url);
	//
	// Log.d("Log in URL is ", url);
	// // currentURL = url;
	//
	// final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	// //nvps.add(new BasicNameValuePair("username", usename));
	// // nvps.add(new BasicNameValuePair("authorid", id));
	//
	// // nvps.add(new BasicNameValuePair("remember_me", version));
	//
	// try {
	// httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	//
	// return HttpRequest.getData(httpost);
	//
	// } catch (final UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// catch (final IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (final URISyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return "error";
	//
	// }

	public static String doremove(String url, final String email,
			final String id) {

		// currentUserName = username;
		// currentPass = pass;

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// currentURL = url;

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", email));
		nvps.add(new BasicNameValuePair("authorid", id));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	public static String doRegister(String url, final String userName,
			final String passWord, final String email, final String appid,
			final String countrycode, final String appversion) {

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// HttpRequest.currentURL = url;

		// Array ( [name] => [pass] => [mail] => )

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", userName));
		nvps.add(new BasicNameValuePair("password", passWord));

		nvps.add(new BasicNameValuePair("email", email));
		nvps.add(new BasicNameValuePair("facebookid", ""));
		nvps.add(new BasicNameValuePair("facebooktoken", ""));

		nvps.add(new BasicNameValuePair("devicetoken", ""));
		nvps.add(new BasicNameValuePair("appid", appid));
		nvps.add(new BasicNameValuePair("countrycode", countrycode));
		nvps.add(new BasicNameValuePair("appversion", appversion));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failed";

	}

	public static String AuthordoRegister(String url, final String username,
			final String firstname, final String lastname, final String alias,
			final String emailnotify) {

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// HttpRequest.currentURL = url;

		// Array ( [name] => [pass] => [mail] => )

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("firstname", firstname));
		nvps.add(new BasicNameValuePair("lastname", lastname));

		nvps.add(new BasicNameValuePair("alias", alias));
		nvps.add(new BasicNameValuePair("emailnotify", emailnotify));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failed";

	}

	// author update

	public static String authorUpdate(String url, final String username,
			final String authorid, final String firstname,
			final String lastname, final String alias, final String emailnotify) {

		final HttpPost httpost = new HttpPost(url);

		Log.d("Log in URL is ", url);
		// HttpRequest.currentURL = url;

		// Array ( [name] => [pass] => [mail] => )

		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("authorid", authorid));
		nvps.add(new BasicNameValuePair("firstname", firstname));
		nvps.add(new BasicNameValuePair("lastname", lastname));

		nvps.add(new BasicNameValuePair("alias", alias));
		nvps.add(new BasicNameValuePair("emailnotify", emailnotify));

		// nvps.add(new BasicNameValuePair("remember_me", version));

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			return HttpRequest.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failed";

	}

	/*
	 * get data as string
	 */

	public static String getData(final HttpPost httpost) throws IOException,
			URISyntaxException {

		String inputLine = "Error";
		final StringBuffer buf = new StringBuffer();

		{

			InputStream ins = null;

			ins = HttpRequest.getUrlData(httpost);

			final InputStreamReader isr = new InputStreamReader(ins);
			final BufferedReader in = new BufferedReader(isr);

			while ((inputLine = in.readLine()) != null) {
				buf.append(inputLine);
			}

			in.close();

		}

		return buf.toString();

	}

	/*
	 * get input stream
	 */
	public static InputStream getUrlData(final HttpPost httpost)
			throws URISyntaxException, ClientProtocolException, IOException {

		final HttpClient client = HttpRequest.getClient();

		// /*
		// *
		// * send cookie
		// */
		//
		// if (CurrentData.getCookies() != null) {
		//
		// System.out.println("Cookie is added to client");
		//
		// for (final Cookie cok : CurrentData.getCookies()) {
		// ((DefaultHttpClient) client).getCookieStore().addCookie(cok);
		// }
		// } else {
		// System.out.println("Cookie is empty");
		// }

		final HttpResponse res = client.execute(httpost);

		System.out.println("post response for  register: "
				+ res.getStatusLine());

		return res.getEntity().getContent();
	}

	/*
	 * get https client
	 */
	public static DefaultHttpClient getClient() {
		DefaultHttpClient ret = null;
		// sets up parameters
		final HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");
		params.setBooleanParameter("http.protocol.expect-continue", false);
		// registers schemes for both http and https
		final SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		final SSLSocketFactory sslSocketFactory = SSLSocketFactory
				.getSocketFactory();
		sslSocketFactory
				.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		registry.register(new Scheme("https", sslSocketFactory, 443));
		final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				params, registry);
		ret = new DefaultHttpClient(manager, params);
		return ret;

	}

	/*
	 * get inputstream for get request
	 */

	public static InputStream getInputStreamForGetRequest(final String url)
			throws URISyntaxException, ClientProtocolException, IOException {
		final DefaultHttpClient httpClient = HttpRequest.getClient();
		URI uri;
		InputStream data = null;

		uri = new URI(url);
		final HttpGet method = new HttpGet(uri);

		// /*
		// *
		// * send cookie
		// */
		//
		// if (CurrentData.getCookies() != null) {
		//
		// System.out.println("Cookie is added to client");
		//
		// for (final Cookie cok : CurrentData.getCookies()) {
		// httpClient.getCookieStore().addCookie(cok);
		// }
		// } else {
		// System.out.println("Cookie is empty");
		// }

		final HttpResponse res = httpClient.execute(method);

		System.out.println("Login form get: " + res.getStatusLine());

		System.out.println("get login cookies:");
		httpClient.getCookieStore().getCookies();

		// if (cookies.isEmpty()) {
		// System.out.println("None");
		// } else {
		//
		// CurrentData.setCookies(cookies);
		//
		// System.out.println("size of cokies " + cookies.size());
		//
		// for (int i = 0; i < cookies.size(); i++) {
		// Log.e("- " + cookies.get(i).toString(), "");
		// }
		// }

		final String code = res.getStatusLine().toString();
		Log.w("status line ", code);
		data = res.getEntity().getContent();

		return data;
	}


	public static String GetText(final InputStream in) throws IOException {
		String text = "";
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				in));
		final StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			text = sb.toString();

		} finally {

			in.close();

		}
		return text;
	}

	public static InputStream GetRequest(final String url)
			throws URISyntaxException, ClientProtocolException, IOException {

		Log.w("Current url is ", url);
		final DefaultHttpClient httpClient = HttpRequest.getClient();
		URI uri;
		InputStream data = null;

		uri = new URI(url);
		final HttpGet method = new HttpGet(uri);

		/*
		 * 
		 * send cookie
		 */

		if (CurrentData.getCookies() != null) {

			System.out.println("Cookie is added to client");

			for (final Cookie cok : CurrentData.getCookies()) {
				httpClient.getCookieStore().addCookie(cok);
			}
		} else {
			System.out.println("Cookie is empty");
		}

		final HttpResponse res = httpClient.execute(method);

		System.out.println("Login form get: " + res.getStatusLine());

		System.out.println("get login cookies:");
		final List<Cookie> cookies = httpClient.getCookieStore().getCookies();

		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {

			CurrentData.setCookies(cookies);

			System.out.println("size of cokies " + cookies.size());

			for (int i = 0; i < cookies.size(); i++) {
				Log.e("- " + cookies.get(i).toString(), "");
			}
		}

		final String code = res.getStatusLine().toString();
		Log.w("status line ", code);
		data = res.getEntity().getContent();

		return data;
	}

}