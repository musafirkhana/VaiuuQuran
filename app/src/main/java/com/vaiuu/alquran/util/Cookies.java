package com.vaiuu.alquran.util;

import java.util.List;

import org.apache.http.cookie.Cookie;

public class Cookies {

	private static List<Cookie> cookies = null;

	/**
	 * @return the cookies
	 */
	public static List<Cookie> getCookies() {
		return Cookies.cookies;
	}

	public static void resetCookies() {
		Cookies.cookies = null;

	}

	/**
	 * @param cookies
	 *            the cookies to set
	 */
	public static void setCookies(final List<Cookie> cookies1) {
		Cookies.cookies = cookies1;
	}

}
