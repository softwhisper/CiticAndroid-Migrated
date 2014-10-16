package curso.and06;

import curso.and06.R;

import android.app.Activity;
import android.app.Fragment;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class NewItemFragment extends Fragment {

	private OnNewItemAddedListener onNewItemAddedListener;

	public interface OnNewItemAddedListener {
		public void onNewItemAdded(String newItem);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// TODO 6 - Cargar el Fragment
		View view = inflater.inflate(R.layout.new_item_fragment, container, false);
		final EditText myEditText = (EditText)view.findViewById(R.id.myEditText);
		
		// TODO 7 - Crear el listener para el EditText
		myEditText.setOnEditorActionListener( new EditText.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					String newItem = myEditText.getText().toString();
					onNewItemAddedListener.onNewItemAdded(newItem);
					myEditText.setText("");
				}
				
				return false;
			}
		});
		
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			onNewItemAddedListener = (OnNewItemAddedListener)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnNewItemAddedListener");
		}
	}

}