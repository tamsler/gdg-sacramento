package com.gdgsacramento.raffleshuffle;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class HttpUtil extends AsyncTask<String, Void, String> {


	
	@Override
	protected String doInBackground(String... params) {
		return makeGetCall(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
    
    static String makeGetCall(String url) {
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        URL myUrl = null;
        URI uri = null;
		try {
			myUrl = new URL(url);
			uri = myUrl.toURI();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        final HttpGet getRequest = new HttpGet(uri);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) { 
                Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url); 
                return null;
            }
            
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
	        	 ByteArrayOutputStream out = new ByteArrayOutputStream();
	             entity.writeTo(out);
	             out.close();
	             return out.toString();
            }
        } catch (Exception e) {
            // Could provide a more explicit error message for IOException or IllegalStateException
            getRequest.abort();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url, e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

}
