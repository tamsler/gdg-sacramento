
package org.gdgsacramento.android.raffle;

import org.gdgsacramento.android.raffle.service.AbstractResultHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements AppConstants {

	private MainApplication mMainApplication;
	
	private EditText mEditTextRaffleName;
	private EditText mEditTextRaffleTicket;
	private Button mButtonRaffleSubmit;
	private Button mButtonRaffleSubmitTicket;
	
	private String mRaffleId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mMainApplication = (MainApplication) getApplication();
        
        mEditTextRaffleName = (EditText) findViewById(R.id.et_raffle_name);
        mEditTextRaffleTicket = (EditText) findViewById(R.id.et_raffle_ticket);
        mButtonRaffleSubmit = (Button) findViewById(R.id.b_raffle_submit);
        mButtonRaffleSubmitTicket = (Button) findViewById(R.id.b_raffle_submit_ticket);
        mButtonRaffleSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Get the raffle name from the EditText field
				String raffleName = mEditTextRaffleName.getText().toString().trim();
				
				if(null == raffleName || "".equals(raffleName)) {
					
					Toast.makeText(MainActivity.this, R.string.raffle_name, Toast.LENGTH_SHORT).show();
					return;
				}
				
				postRaffle(raffleName);
			}
		});
        mButtonRaffleSubmitTicket.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String ticketName = mEditTextRaffleTicket.getText().toString().trim();
				
				if(null == mRaffleId || "".equals(mRaffleId)) {
					
					Toast.makeText(MainActivity.this, R.string.define_raffle, Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(null == ticketName || "".equals(ticketName)) {
					
					Toast.makeText(MainActivity.this, R.string.ticket_name, Toast.LENGTH_SHORT).show();
					return;
				}
				
				postTicket(ticketName, mRaffleId);
			}
		});
    }
    
    private void postRaffle(String raffleName) {
    	
    	// Create raffle via service
		mMainApplication.raffleService.postRaffle(raffleName, new AbstractResultHandler<String>() {
			
			@Override
			public void onResult(String json) {
				
				try {
					
					JSONArray jsonArray = new JSONArray(json);
					
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					
					mRaffleId = jsonObject.getString(RAFFLE_JSON_ID);
					
					Toast.makeText(MainActivity.this, R.string.create_raffle, Toast.LENGTH_SHORT).show();
				}
				catch (JSONException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError() {
				
				// TODO Auto-generated method stub
			}
		});
    }
    
    private void postTicket(String name, String raffleId) {
    	
    	mMainApplication.raffleService.postTicket(name, raffleId, new AbstractResultHandler<String>() {
    		
    		@Override
    		public void onResult() {
    			
    			Toast.makeText(MainActivity.this, R.string.enter_ticket, Toast.LENGTH_SHORT).show();
    		}
		});
    }
}
