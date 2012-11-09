/*
 * Copyright 2012 Thomas Amsler
 * 
 * Author:  Thomas Amsler : tamsler@gmail.com
 * 
 */

package org.gdgsacramento.android.raffle.service;

public abstract class AbstractResultHandler<T> implements ResultHandler<T> {

	
	
	
	public void onError() {
		
		// Default
	}
	
	public void onError(int status) {
		
		// Default
	}
	
	public void onOffline() {

		// Default
	}

	public void onResult() {

		// Default
	}

	public void onResult(T data) {

		// Default
	}
}
