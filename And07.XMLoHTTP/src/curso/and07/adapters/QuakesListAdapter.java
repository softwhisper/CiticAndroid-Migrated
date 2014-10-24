package curso.and07.adapters;

import java.util.ArrayList;


import curso.and07.FragmentList;
import curso.and07.Quake;
import curso.and07.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author pablo
 *
 */
public class QuakesListAdapter extends BaseAdapter {
	private final static String TAG = "QuakeListAdapter";
	
	private Context context;
	private LayoutInflater li;
	private ArrayList<Quake> quakesList = new ArrayList<Quake>();

	public QuakesListAdapter(Context context, ArrayList<Quake> list) {
		super();
		this.context = context;
		this.quakesList = list;
		li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		Log.d(TAG, "getCount: " + quakesList.size());
		return quakesList.size();
	}

	@Override
	public Object getItem(int position) {
		Log.d(TAG, "getItem");
		return quakesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		Log.d(TAG, "getItemId");
		return (long)position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "getView");
		View v;
		
		if (convertView != null) {
			v = convertView;
		} else {
			v = li.inflate(android.R.layout.simple_list_item_1, null);
		}

		TextView txtView = (TextView)v.findViewById(android.R.id.text1);		
		txtView.setText(quakesList.get(position).toString());

		return v;
	}

}
