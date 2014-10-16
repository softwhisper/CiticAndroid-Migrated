package curso.and02.alumni;

import curso.and02.alumni.R;
import curso.and02.alumni.models.Alumno;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class AlimniDetail extends Activity {
	
	TextView nombre;
	TextView apellido;
	TextView grupo;
	TextView area;
	TextView asiste;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alimni_detail);
		
		Alumno alm = (Alumno)getIntent().getExtras().get("alumno");
		
		nombre = (TextView)findViewById(R.id.textView1);
		apellido = (TextView)findViewById(R.id.textView2);
		grupo = (TextView)findViewById(R.id.textView3);
		area = (TextView)findViewById(R.id.textView4);
		asiste = (TextView)findViewById(R.id.textView5);
		
		nombre.setText(alm.getName());
		apellido.setText(alm.getLastname());
		grupo.setText(alm.getGroupo());
		area.setText(alm.getArea());
		asiste.setText(alm.isIs_present() ? "SI" : "No");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alimni_detail, menu);
		return true;
	}

}
