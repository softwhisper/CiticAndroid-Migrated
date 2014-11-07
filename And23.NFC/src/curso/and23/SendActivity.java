package curso.and23;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import curso.and23.R;

public class SendActivity extends Activity {

    private NfcAdapter mAdapter;
    private TextView mText;
    private NdefMessage mMessage;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_send);
        mText = (TextView) findViewById(R.id.textView1);
        
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mMessage = new NdefMessage(NdefRecord.createUri("http://www.android.com"));

        if (mAdapter != null) {
            mAdapter.setNdefPushMessage(mMessage, this);
            mText.setText("Enviado TAG con las url android.com");
        } else {
            mText.setText("Tu tlf no dispone de NFC");
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.send, menu);
		return true;
	}

}
