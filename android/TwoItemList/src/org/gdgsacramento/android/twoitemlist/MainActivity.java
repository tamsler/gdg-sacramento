package org.gdgsacramento.android.twoitemlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	private List<ListItem> mItems;
	private ListView mListView;
	private TwoItemAdapter mTwoItemAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mItems = new ArrayList<ListItem>();
        
        for(int i = 0; i < 100; i++) {
        	
        	mItems.add(new ListItem("Foo " + Integer.toString(i), "Bar " + Integer.toString(i)));
        }
        
        mTwoItemAdapter = new TwoItemAdapter(this, R.layout.two_items, mItems);
        
        mListView = (ListView) findViewById(R.id.listView1);
        mListView.setAdapter(mTwoItemAdapter);
    }
}
