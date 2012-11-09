package org.gdgsacramento.android.raffle;


import org.gdgsacramento.android.raffle.service.AbstractResultHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private MainApplication mMainApplication;
	
	private EditText mEditTextRaffleName;
	private TextView mTextViewRaffleOutput;
	private Button mButtonRaffleSubmit;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mMainApplication = (MainApplication) getApplication();
        
        mEditTextRaffleName = (EditText) findViewById(R.id.et_raffle_name);
        mTextViewRaffleOutput = (TextView) findViewById(R.id.tv_raffle_data);
        mButtonRaffleSubmit = (Button) findViewById(R.id.b_raffle_submit);
        mButtonRaffleSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Get the raffle name from the EditText field
				String raffleName = mEditTextRaffleName.getText().toString().trim();
				
				// TODO: raffle name error checking
				postRaffle(raffleName);
			}
		});
    }
    
    private void postRaffle(String raffleName) {
    	
    	// Create raffle via service
		mMainApplication.raffleService.postRaffle(raffleName, new AbstractResultHandler<String>() {
			
			@Override
			public void onResult(String json) {
				
				mTextViewRaffleOutput.setText(json);
				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
		});
    }
}
