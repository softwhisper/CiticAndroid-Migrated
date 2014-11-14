package and25.completo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import and25.completo.adapters.StudentArrayAdapter;
import and25.completo.fragments.StudentListFragment;
import and25.completo.models.Student;
import and25.completo.shared.VolleySingleton;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;


public class MainActivity extends Activity {

	private StudentListFragment stdListFragment;
	private ArrayList<Student> students;
	private StudentArrayAdapter aa;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        students = new ArrayList<Student>();
        
        FragmentManager fm = getFragmentManager();        
        stdListFragment = (StudentListFragment)fm.findFragmentById(R.id.fragment1);
                
        aa = new StudentArrayAdapter(this, R.layout.student_list_item, students);
        stdListFragment.setListAdapter(aa);   
        
        stdListFragment.getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
				Student s = students.get(pos);
				
				Intent intent = new Intent(MainActivity.this, NewStudentActivity.class); // Vista de detalle
				intent.putExtra("student", s);
				
				startActivity(intent);
			}
        	
		});
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	
    	JsonArrayRequest jArrayReq = new JsonArrayRequest("http://curso.softwhisper.es/stundents.json", 
    	new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray jArray) {
				students.clear();
				for (int i = 0; i < jArray.length(); i++) {					
					try {
						JSONObject jObject = jArray.getJSONObject(i);
						Log.d("PARSER", jObject.toString());
						
						Gson gson = new Gson();
						Student student = gson.fromJson(jObject.toString(), Student.class);						
						Student.save(getApplicationContext(), Student.class, student);
						students.add(student);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}					
				}	
				aa.notifyDataSetChanged();
			}		
    	}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(MainActivity.this, "Error en la conexión" + error.getMessage(), Toast.LENGTH_LONG).show();
			}    		
		});
    	    	
    	jArrayReq.setShouldCache(false);
    	VolleySingleton.getInstance().getRequestQueue().add(jArrayReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            return true;            
        } else if (id == R.id.add_student) {
        	Intent intent = new Intent(this, NewStudentActivity.class);
        	startActivity(intent);
        	return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
