/*
 * Copyright 2012 Thomas Amsler
 * 
 * Author:  Thomas Amsler : tamsler@gmail.com
 * 
 */

package org.gdgsacramento.android.raffle.task;


public abstract class AbstractHttpTaskNotifier implements HttpTaskNotifier {

	abstract public void doProcess(String content);

	public void doCancel() {

		// Default
	}

	public void onError(int errorCode, String content) {

		// Default
	}
}
