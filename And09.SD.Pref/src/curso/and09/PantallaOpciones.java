package curso.and09;

import curso.and09.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PantallaOpciones extends PreferenceActivity {
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.opciones);
    }
}
