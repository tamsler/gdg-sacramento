/*
 * Copyright 2012 Thomas Amsler
 * 
 * Author:  Thomas Amsler : tamsler@gmail.com
 * 
 */

package org.gdgsacramento.android.raffle.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.gdgsacramento.android.raffle.AppConstants;

import android.os.AsyncTask;
import android.util.Log;

public class HttpTask extends AsyncTask<Void, Void, String> implements AppConstants {

	private HttpTaskNotifier mNotifier;

	private HttpResponse mHttpResponse;
	private int mHttpStatusCodeOk;
	private int mHttpStatusCode;
	private int mErrorCode = AppConstants.NO_ERROR;
	private int mHttpMethod;
	private String mApiKey;
	private String mBasicAuth;
	private StringEntity mStringEntity;
	private HttpRequestArgs mHttpRequestArgs;

	/*
	 * @param notifier : Any class that implements the GetContentTaskNotifier
	 * API
	 * 
	 * @param httpStatusCodeOk : An HTTP status OK code e.g. HttpStatus.SC_OK
	 * (200)
	 */
	public HttpTask(HttpRequestArgs httpRequestArgs, int httpStatusCodeOk, int httpMethod, HttpTaskNotifier notifier) {

		mNotifier = notifier;
		mHttpStatusCodeOk = httpStatusCodeOk;
		mHttpMethod = httpMethod;
		mHttpRequestArgs = httpRequestArgs;
	}

	@Override
	protected String doInBackground(Void... args) {

		mApiKey = mHttpRequestArgs.getApiKey();

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, HTTP_CLIENT_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, HTTP_CLIENT_SO_TIMEOUT);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

		try {

			switch (mHttpMethod) {

			case HTTP_GET:

				HttpGet httpGet = new HttpGet(mHttpRequestArgs.getUrl());

				if (null != mApiKey && !"".equals(mApiKey)) {

					httpGet.setHeader(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_SCHEME_API_KEY + mApiKey);
				}

				mBasicAuth = mHttpRequestArgs.getBasicAuth();

				if (null != mBasicAuth && !"".equals(mBasicAuth)) {

					httpGet.setHeader(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_SCHEME_BASIC + mBasicAuth);
				}

				mHttpResponse = httpClient.execute(httpGet);

				break;

			case HTTP_POST:

				Log.i(LOG_TAG, "DEBUG: url = " + mHttpRequestArgs.getUrl());
				
				HttpPost httpPost = new HttpPost(mHttpRequestArgs.getUrl());

				if (null != mApiKey && !"".equals(mApiKey)) {

					httpPost.setHeader(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_SCHEME_API_KEY + mApiKey);
				}

				mStringEntity = new StringEntity(mHttpRequestArgs.getJson().toString());
				mStringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, HTTP_CONTENT_TYPE_APPLICATION_JSON));
				httpPost.setEntity(mStringEntity);

				mHttpResponse = httpClient.execute(httpPost);

				break;

			case HTTP_DELETE:

				HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(mHttpRequestArgs.getUrl());

				if (null != mApiKey && !"".equals(mApiKey)) {

					httpDelete.setHeader(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_SCHEME_API_KEY + mApiKey);
				}

				mStringEntity = new StringEntity(mHttpRequestArgs.getJson().toString());
				mStringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, HTTP_CONTENT_TYPE_APPLICATION_JSON));
				httpDelete.setEntity(mStringEntity);

				mHttpResponse = httpClient.execute(httpDelete);

				break;

			case HTTP_PUT:

				break;
			}
		}
		catch (IllegalArgumentException e) {
			mErrorCode = AppConstants.REST_ERROR;
			Log.e(LOG_TAG, "EXCEPTION", e);
			return null;
		}
		catch (ClientProtocolException e) {
			mErrorCode = AppConstants.REST_ERROR;
			Log.e(LOG_TAG, "EXCEPTION", e);
			return null;
		}
		catch (IOException e) {
			mErrorCode = AppConstants.REST_ERROR;
			Log.e(LOG_TAG, "EXCEPTION", e);
			return null;
		}

		// Checking if the task has been canceled
		if (isCancelled()) {
			return null;
		}

		mHttpStatusCode = mHttpResponse.getStatusLine().getStatusCode();

		if (mHttpStatusCodeOk != mHttpStatusCode) {

			Log.w(LOG_TAG, "WARN: HttpPostTask status code = " + mHttpStatusCode);
			mErrorCode = AppConstants.REST_ERROR;
			
			// Don't return so that we get the error content/message if one was provided 
		}

		// Checking if the task has been canceled
		if (isCancelled()) {
			return null;
		}

		if (HttpStatus.SC_NO_CONTENT == mHttpStatusCode) {

			return "";
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		HttpEntity httpEntity = mHttpResponse.getEntity();

		if (null == httpEntity) {

			mErrorCode = AppConstants.REST_ERROR;
			return null;
		}

		try {

			httpEntity.writeTo(out);
			out.close();
		}
		catch (IOException ioe) {

			mErrorCode = AppConstants.REST_ERROR;
			return null;
		}

		return out.toString();
	}

	@Override
	protected void onPostExecute(String content) {

		if (AppConstants.NO_ERROR != mErrorCode) {

			mNotifier.onError(mHttpStatusCode, content);
		}
		else {

			mNotifier.doProcess(content);
		}
	}

	@Override
	protected void onCancelled() {

		mNotifier.doCancel();
	}
}
