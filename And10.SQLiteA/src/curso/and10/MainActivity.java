package curso.and10;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import curso.and10.R;

public class MainActivity extends Activity {
	
	Button btnCreate;
	Button btnUpdate;
	Button btnRead;
	EditText text;
	DataBaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private void createContacts() {
		// TODO
	}
	
	private void updateContacts() {
		// TODO
	}
	
	private void readContacts() {
		// TODO
	}
}