package and25.completo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import and25.completo.models.Student;
import and25.completo.shared.VolleySingleton;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class NewStudentActivity extends Activity {

	private EditText editTextName;
	private EditText editTextEmail;
	private EditText editTextCity;
	
	private Student s;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_student);
		
		bindUI();
		setListeners();
		
		Intent i = getIntent();
		s = i.getParcelableExtra("student");
		
		
	}

	private void bindUI() {
		editTextName = (EditText)findViewById(R.id.editTextName);
		editTextEmail = (EditText)findViewById(R.id.editTextEmail);
		editTextCity = (EditText)findViewById(R.id.editTextCity);
		
		editTextCity.setText(s.getCity());
	}
	
	private void setListeners() {
		editTextName.setOnEditorActionListener(new OnEditorActionListener() {			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					editTextEmail.requestFocus();
				}				
				return true;
			}
		});
		editTextEmail.setOnEditorActionListener(new OnEditorActionListener() {			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					editTextCity.requestFocus();
				}				
				return true;
			}
		});		
		editTextCity.setOnEditorActionListener(new OnEditorActionListener() {			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					postStudent();
				}				
				return true;
			}
		});
		((Button)findViewById(R.id.btnCreateStudent)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				postStudent();
			}
		});
	}
	
	private void postStudent() {
		
		Map<String, String> params = new HashMap<String, String>();		
		params.put("name", editTextName.getText().toString());
		params.put("city", editTextCity.getText().toString());
		params.put("email", editTextEmail.getText().toString());
		
		Map<String, Map> lastParams = new HashMap<String, Map>();
		lastParams.put("stundent", params);
		
		JSONObject jBody = new JSONObject(lastParams);
		
				
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, 
				"http://curso.softwhisper.es/stundents.json", 
				jBody,
		new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject jObject) {
				Log.d("POST", jObject.toString());
				
				Gson gson = new Gson();
				Student student = gson.fromJson(jObject.toString(), Student.class);						
				Student.save(getApplicationContext(), Student.class, student);
				
				finish();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				Log.e("POST", arg0.getMessage());
				Toast.makeText(NewStudentActivity.this, arg0.getMessage(), Toast.LENGTH_LONG).show();
			}
			
		});
		
		VolleySingleton.getInstance().getRequestQueue().add(request);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_student, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
