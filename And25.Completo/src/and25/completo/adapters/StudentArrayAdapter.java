package and25.completo.adapters;

import and25.completo.models.Student;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;



public class StudentArrayAdapter extends ArrayAdapter<Student> {

	public StudentArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return convertView;
	}

}
