package org.gdgsacramento.android.raffle;

import org.gdgsacramento.android.raffle.service.RaffleService;

import android.app.Application;

public class MainApplication extends Application {
	
	public RaffleService raffleService;
	
	@Override
	public void onCreate() {
		
		super.onCreate();
		
		raffleService = new RaffleService(this);
	}

}
