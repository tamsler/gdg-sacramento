package org.gdgsacramento.android.simplelist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView mListView;
	List<String> mData;
	ArrayAdapter<String> mArrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.list_view);

		mData = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {

			mData.add("Item " + Integer.toString(i));
		}

		mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mData);
		
		mListView.setAdapter(mArrayAdapter);
	}
}
