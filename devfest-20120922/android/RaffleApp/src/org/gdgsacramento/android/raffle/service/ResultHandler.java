/*
 * Copyright 2012 Thomas Amsler
 * 
 * Author:  Thomas Amsler : tamsler@gmail.com
 * 
 */

package org.gdgsacramento.android.raffle.service;

public interface ResultHandler<T> {
	
	public void onResult();
	
	public void onResult(T data);
	
	public void onError();
	
	public void onError(int status);
	
	public void onOffline();
}
