package com.flu.TrojanSMS;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneStateListener extends PhoneStateListener {
	Context context;
	
	public MyPhoneStateListener(Context c){
		this.context=c;
	}
	public void onCallStateChanged(int state, String incomingNumber) {
		switch(state){
			case TelephonyManager.CALL_STATE_IDLE:
		      break;
		    case TelephonyManager.CALL_STATE_OFFHOOK:
		      break;
		    case TelephonyManager.CALL_STATE_RINGING:
		      Log.d("DEBUG", "RINGING " + incomingNumber);
		      break;
	    }
	}
}
