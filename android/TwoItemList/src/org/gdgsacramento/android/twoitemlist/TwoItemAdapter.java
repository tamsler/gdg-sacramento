package org.gdgsacramento.android.twoitemlist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class TwoItemAdapter extends ArrayAdapter<ListItem> {

	private Context mContext;
	private List<ListItem> mItems;
	
	public TwoItemAdapter(Context context, int textViewResourceId, List<ListItem> objects) {

		super(context, textViewResourceId, objects);
		
		mContext = context;
		mItems = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		ViewHolder viewHolder;
		
		if(null == row) {
			
			LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflator.inflate(R.layout.two_items, parent, false);
			
			viewHolder = new ViewHolder();
			viewHolder.text1 = (TextView) row.findViewById(R.id.tv_1);
			viewHolder.text2 = (TextView) row.findViewById(R.id.tv_2);
			
			row.setTag(R.string.view_holder_key, viewHolder);
		}
		else {
			
			viewHolder = (ViewHolder) row.getTag(R.string.view_holder_key);
		}
		
		ListItem listItem = mItems.get(position);
		viewHolder.text1.setText(listItem.text1);
		viewHolder.text2.setText(listItem.text2);
		
		return row;
	}
	
	
	static class ViewHolder {
		
		public TextView text1;
		public TextView text2;
	}
}
