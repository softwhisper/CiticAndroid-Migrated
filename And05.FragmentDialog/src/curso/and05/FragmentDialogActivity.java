
package curso.and05;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import curso.and05.R;
import curso.and05.EditNameDialog.EditNameDialogListener;

/**
 * 
 * @author pablo
 *
 */
public class FragmentDialogActivity extends FragmentActivity implements EditNameDialogListener {
	
	EditNameDialog editname;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        showEditDialog();
    }

    private void showEditDialog() {
       FragmentManager fm = getSupportFragmentManager();
       if (editname == null) {
    	    editname = new EditNameDialog();
       		editname.show(fm, "dia_edit_name");
       }
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "hola, " + inputText, Toast.LENGTH_LONG).show();
    }
    
    

}
