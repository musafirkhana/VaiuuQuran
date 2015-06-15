package com.vaiuu.alquran.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

public class HTTPPostHelper {

	private String URL = "";
	private List<NameValuePair> nvps = new ArrayList<NameValuePair>();

	public HTTPPostHelper(String uRL, List<NameValuePair> nvps) {
		super();
		URL = uRL;
		this.nvps = nvps;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public List<NameValuePair> getNvps() {
		return nvps;
	}

	public void setNvps(List<NameValuePair> nvps) {
		this.nvps = nvps;
	}

}
