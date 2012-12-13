package org.gdgsacramento.android.raffle.service;

import org.apache.http.HttpStatus;
import org.gdgsacramento.android.raffle.AppConstants;
import org.gdgsacramento.android.raffle.MainApplication;
import org.gdgsacramento.android.raffle.task.AbstractHttpTaskNotifier;
import org.gdgsacramento.android.raffle.task.HttpRequestArgs;
import org.gdgsacramento.android.raffle.task.HttpTask;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class RaffleService implements AppConstants {

	private MainApplication mMainApplication;

	public RaffleService(Context context) {

		mMainApplication = (MainApplication) context;
	}

	public void postTicket(String name, String raffleId,
			final ResultHandler<String> resultHandler) {

		// Create JSON
		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put(RAFFLE_ID, raffleId);
			jsonObject.put(TICKET_NAME, name);

		} catch (JSONException e) {

			Log.e(LOG_TAG, "EXCEPTION", e);
		}

		// Create Http Request Args
		HttpRequestArgs httpRequestArgs = new HttpRequestArgs();
		httpRequestArgs.setUrl(POST_TICKET);
		httpRequestArgs.setJson(jsonObject);

		// Create Http Task
		HttpTask httpTask = new HttpTask(httpRequestArgs, HttpStatus.SC_OK,
				HTTP_POST, new AbstractHttpTaskNotifier() {

					@Override
					public void doProcess(String content) {


						resultHandler.onResult();
					}
				});

		httpTask.execute();

	}

	public void postRaffle(String raffleName,
			final ResultHandler<String> resultHandler) {

		// Error checking
		if (null == raffleName || "".equals(raffleName)) {

			// TODO: Show error message
			return;
		}

		// Create JSON
		JSONObject jsonObject = new JSONObject();

		try {

			jsonObject.put(RAFFLE_NAME, raffleName);
		} catch (JSONException e) {

			Log.e(LOG_TAG, "EXCEPTION", e);
		}

		// Create Http Request Args
		HttpRequestArgs httpRequestArgs = new HttpRequestArgs();
		httpRequestArgs.setUrl(POST_RAFFLE);
		httpRequestArgs.setJson(jsonObject);

		// Create Http Task
		HttpTask httpTask = new HttpTask(httpRequestArgs, HttpStatus.SC_OK,
				HTTP_POST, new AbstractHttpTaskNotifier() {

					@Override
					public void doProcess(String content) {

						// Validate content
						Log.i(LOG_TAG, "DEBUG: content = " + content);

						if (null == content || "".equals(content)) {

							resultHandler.onError();
						} else {

							resultHandler.onResult(content);
						}
					}
				});

		httpTask.execute();
	}
}
