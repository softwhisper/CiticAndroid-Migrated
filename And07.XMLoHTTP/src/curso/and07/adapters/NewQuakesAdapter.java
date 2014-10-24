package curso.and07.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import curso.and07.Quake;
import curso.and07.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewQuakesAdapter extends BaseAdapter {
	private final static String TAG = "NewQuakesAdapter";
	
	private Context context;
	private LayoutInflater li;
	private ArrayList<Quake> quakesList = new ArrayList<Quake>();
	
	public NewQuakesAdapter(Context context, ArrayList<Quake> quakesList) {
		super();
		this.context = context;
		this.quakesList = quakesList;
		
		li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return quakesList.size();
	}

	@Override
	public Object getItem(int position) {
		return quakesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return (long)position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		
		if (convertView != null) {
			v = convertView;
		} else {
			v = li.inflate(R.layout.new_list_item, null);
		}
		
		TextView txtMag = (TextView)v.findViewById(R.id.txtMagnitud);
		TextView txtDet = (TextView)v.findViewById(R.id.txtDetails);
		TextView txtDate = (TextView)v.findViewById(R.id.txtDate);
		
		Quake q = quakesList.get(position);
		
		txtMag.setText(String.valueOf(q.getMagnitude()));
		txtDet.setText(q.getDetails());
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");		
		txtDate.setText(sdf.format(q.getDate()));
		
		return v;
	}

}
