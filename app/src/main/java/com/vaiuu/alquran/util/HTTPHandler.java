package com.vaiuu.alquran.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

/**
 * HTTPHandler This class handles POST and GET requests and enables you to
 * upload files via post. Cookies are stored in the HttpClient.
 * 
 * @author Sander Borgman
 * @url http://www.sanderborgman.nl
 * @version 1
 */
public class HTTPHandler {

	/*
	 * 
	 * do log in
	 */

	/*
	 * get https client
	 */
	public static DefaultHttpClient getClient() {

		DefaultHttpClient ret = null;
		// sets up parameters
		final HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		HttpProtocolParams.setUserAgent(params, "PokerBuddies"); // new addedr

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
	 * get data as string
	 */

	public static String getData(final HttpPost httpost) throws IOException,
			URISyntaxException {

		String inputLine = "Error";
		final StringBuffer buf = new StringBuffer();

		{

			InputStream ins = null;

			ins = HTTPHandler.getUrlData(httpost);

			final InputStreamReader isr = new InputStreamReader(ins);
			final BufferedReader in = new BufferedReader(isr);

			while ((inputLine = in.readLine()) != null) {
				buf.append(inputLine);
			}

			in.close();

		}

		return buf.toString();

	}

	public static InputStream getInputStreamForGetRequest(final String url)
			throws URISyntaxException, ClientProtocolException, IOException {
		final DefaultHttpClient httpClient = HTTPHandler.getClient();

		URI uri;
		uri = new URI(url);
		final HttpGet method = new HttpGet(uri);

		method.addHeader("Accept-Encoding", "gzip");
		method.addHeader("Accept-Encoding", "utf-8");

		/*
		 * 
		 * send cookie
		 */

		if (Cookies.getCookies() != null) {

			System.out.println("Cookie is added to client");

			for (final Cookie cok : Cookies.getCookies()) {
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

			Cookies.setCookies(cookies);

			System.out.println("size of cokies " + cookies.size());

			for (int i = 0; i < cookies.size(); i++) {
				Log.e("- " + cookies.get(i).toString(), "");
			}
		}

		final Header h1 = res.getEntity().getContentEncoding();

		Log.w("hearder line ", h1.toString());

		final String code = res.getStatusLine().toString();
		Log.w("status line ", code);

		InputStream instream = res.getEntity().getContent();
		final Header contentEncoding = res.getFirstHeader("Content-Encoding");
		if (contentEncoding != null
				&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			instream = new GZIPInputStream(instream);
		}

		return instream;
	}

	/**
	 * Get string from stream
	 * 
	 * @param InputStream
	 * @return
	 * @throws IOException
	 */
	public static String GetText(final InputStream in) throws IOException {
		String text = "";

		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				in, "UTF-8"));
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

	public static String GetDataFromURL(String url) throws URISyntaxException,
			ClientProtocolException, IOException {

		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */


		final DefaultHttpClient client = getClient();
		HttpGet method;

		method = new HttpGet(new URI(url));
		final HttpResponse res = client.execute(method);

		final String code = res.getStatusLine().toString();
		Log.w("status line ", code);

		InputStream instream = res.getEntity().getContent();
		final Header contentEncoding = res.getFirstHeader("Content-Encoding");
		if (contentEncoding != null
				&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			Log.w("encoding is gzip", "processing now");
			instream = new GZIPInputStream(instream);
		}

		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				instream, "UTF-8"));
		final StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				instream.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	/*
	 * get inputstream for get request
	 */

	/*
	 * get input stream
	 */
	public static InputStream getUrlData(final HttpPost httpost)
			throws URISyntaxException, ClientProtocolException, IOException {

		final HttpClient client = HTTPHandler.getClient();

		/*
		 * 
		 * send cookie
		 */

		if (Cookies.getCookies() != null) {

			System.out.println("Cookie is added to client");

			for (final Cookie cok : Cookies.getCookies()) {
				((DefaultHttpClient) client).getCookieStore().addCookie(cok);
			}
		} else {
			System.out.println("Cookie is empty");
		}

		final HttpResponse res = client.execute(httpost);

		System.out.println("Login form get: " + res.getStatusLine());

		System.out.println("get login cookies:");
		final List<Cookie> cookies = ((DefaultHttpClient) client)
				.getCookieStore().getCookies();

		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {

			Cookies.setCookies(cookies);

			System.out.println("size of cokies " + cookies.size());

			for (int i = 0; i < cookies.size(); i++) {
				Log.e("- " + cookies.get(i).toString(), "");
			}
		}

		// Log.w("response",res.g)

		return res.getEntity().getContent();
	}

	/*
	 * get inputstream for get request
	 */

	public static void refreshURL(final String url) throws URISyntaxException,
			ClientProtocolException, IOException {
		final DefaultHttpClient httpClient = HTTPHandler.getClient();

		URI uri;

		uri = new URI(url);
		final HttpGet method = new HttpGet(uri);

		final HttpResponse res = httpClient.execute(method);

		System.out.println("Status line for refresh " + res.getStatusLine());

	}

	public static String GetPostDataFromURL(HTTPPostHelper helper) {

		final HttpPost httpost = new HttpPost(helper.getURL().trim());

		Log.d("Log in URL is ", helper.getURL().trim() + "");

		try {
			httpost.setEntity(new UrlEncodedFormEntity(helper.getNvps(),
					HTTP.UTF_8));

			return HTTPHandler.getData(httpost);

		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}


	// editprofile: myId, email,password,photo => /
	// TODO: to make it more efficient, don't create http client every time
//	public static String postDataWithSingleImage(
//			HTTPPostWithSimpleMultipart helper) {
//
//		final HttpPost httpost = new HttpPost(helper.getURL().trim());
//
//		Log.d("Log in URL is ", helper.getURL().trim() + "");
//
//		try {
//			httpost.setEntity(helper.getEntity());
//
//			return HTTPHandler.getData(httpost);
//
//		} catch (final UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (final IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (final URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "error";
//	}

//	public static String postDataWithMultipartImage(String url,
//			MultipartEntity entity) {
//
//		final HttpPost httpost = new HttpPost(url.trim());
//
//
//		try {
//			httpost.setEntity(entity);
//
//			return HTTPHandler.getData(httpost);
//
//		} catch (final UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (final IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (final URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "error";
//	}

	
}