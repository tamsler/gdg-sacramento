/*
 * Copyright 2012 Thomas Amsler
 * 
 * Author:  Thomas Amsler : tamsler@gmail.com
 * 
 */

package org.gdgsacramento.android.raffle.task;

public interface HttpTaskNotifier {
	
	public void doProcess(String content);
	
	public void doCancel();
	
	public void onError(int errorCode, String content);
}
