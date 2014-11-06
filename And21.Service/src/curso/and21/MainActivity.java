package curso.and21;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import curso.and21.R;

public class MainActivity extends ListActivity {

	private LocalWordService s;
	private ArrayAdapter<String> adapter;
	private List<String> wordList;
	
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wordList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				wordList);
		setListAdapter(adapter);
		doBindService();
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			s = ((LocalWordService.MyBinder) binder).getService();
			Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
					.show();
		}

		public void onServiceDisconnected(ComponentName className) {
			Log.d("SERVICE", "DISCONECTED");
			s = null;
		}
	};

	void doBindService() {
		bindService(new Intent(this, LocalWordService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	public void showServiceData(View view) {
		if (s != null) {

			Toast.makeText(this, "Number of elements" + s.getWordList().size(),
					Toast.LENGTH_SHORT).show();
			wordList.clear();
			wordList.addAll(s.getWordList());
			adapter.notifyDataSetChanged();
		}
	}

}
