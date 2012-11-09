/*
 * Copyright 2012 Thomas Amsler
 * 
 * Author:  Thomas Amsler : tamsler@gmail.com
 * 
 */

package org.gdgsacramento.android.raffle.task;

import org.gdgsacramento.android.raffle.AppConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Base64;
import android.util.Log;

public class HttpRequestArgs {

	private JSONObject jsonObject;
	private JSONArray jsonArray;
	private String url;
	private String apiKey;
	private String userId;
	private String password;
	
	public void setJson(JSONObject json) {
		
		this.jsonObject = json;
	}
	
	public void setJson(JSONArray json) {
		
		this.jsonArray = json;
	}
	
	public String getJson() {
		
		if(null != jsonObject && null == jsonArray) {
			
			return jsonObject.toString();
		}
		else if(null != jsonArray && null == jsonObject) {
			
			return jsonArray.toString();
		}
		else {
			
			Log.e(AppConstants.LOG_TAG, "ERROR: JSON is null");
			return "";
		}
	}
	
	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public String getApiKey() {

		return apiKey;
	}

	public void setApiKey(String apiKey) {

		this.apiKey = apiKey;
	}

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}
	
	public String getBasicAuth() {
		
		String encodedBasicAuth = null;
		
		if (null != userId && null != password && !"".equals(userId) && !"".equals(password)) {
			
			StringBuilder basicAuth = new StringBuilder(userId);
			basicAuth.append(":");
			basicAuth.append(password);
			encodedBasicAuth = Base64.encodeToString(basicAuth.toString().getBytes(), Base64.NO_WRAP);
		}

		return encodedBasicAuth;
	}	
}
