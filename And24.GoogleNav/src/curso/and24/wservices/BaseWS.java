package curso.and24.wservices;

import java.util.HashMap;
import java.util.Map;

import curso.and24.network.Rest;
import curso.and24.network.Rest.RequestMethod;
import curso.and24.shared.Constants;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
/**
 * This is a "abstract" subclass of Service that uses a worker thread to handle all start requests, one at a time. 
 * This is the best option if you don't require that your service handle multiple requests simultaneously. 
 * All you need to do is implement onHandleIntent(), which receives the intent for each start request so 
 * you can do the background work.
 * 
 */
public abstract class BaseWS extends IntentService implements Constants {
	
	private static final String QUERY = "query";
	private static final String COMMAND = "command";
	private static final String RECEIVER = "receiver";
	
	private ResultReceiver receiver = null;
	protected Rest petition = null;
	protected String url = null;
	protected String petitionResponse = null;
	protected HashMap<String, String>params = null;
	protected Bundle bundle = null;
	protected boolean includeHeader = true;
	
	public BaseWS(String TAG){
		super(TAG);
	}
	
	/**
	 * This method launches the petition and contains the logic of the parser result.
	 * NEEDED TO IMPLEMENT IN A SUBCLASS
	 * @throws Exception 
	 **/
	abstract protected Bundle startPetition() throws Exception;
	
	/**
	 * This method launches the petition and contains the logic of the parser result.
	 * NEEDED TO IMPLEMENT IN A SUBCLASS IF IS A WS WITHOUT RESPONSE
	 * @throws Exception 
	 **/
	 abstract protected void startPetitionWithoutResponse() throws Exception;

	
	/**
	 * Launches the GET petition
	 * @return
	 * @throws Exception 
	 */
	protected String executeGetPetition() throws Exception{
		bundle = new Bundle();
		petition = new Rest(url);

		if (params != null){
			for (Map.Entry<String, String> entry : params.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    petition.AddParam(key, value);
			}
		}
		petition.Execute(RequestMethod.GET);
    	return petition.getResponse();
	}

	/**
	 * Launches the POST petition
	 * @return
	 * @throws Exception 
	 */
	protected String executePostPetition() throws Exception{
		bundle = new Bundle();
		petition = new Rest(url);

		if (params != null){
			for (Map.Entry<String, String> entry : params.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
			    petition.AddParam(key, value);
			}
		}
		petition.Execute(RequestMethod.POST);
    	return petition.getResponse();
	}
	
	/**
	 * Manages the result of the petition and send a message to the activity whose implements MyResultReceiver
	 * @param intent
	 */ 
	@Override
    protected void onHandleIntent(Intent intent) {
        receiver = intent.getParcelableExtra(RECEIVER);
        String command = intent.getStringExtra(COMMAND);
        launchCommand(command); 
        this.stopSelf();
    }
	
	/*
	 * LAunches a command
	 */
	private void launchCommand(String command){
		 if(command != null && command.equals(QUERY)) {		//USED FOR WS PETITIONS CALLED FROM ACTIVITIES
	            receiver.send(Constants.STATUS_RUNNING, Bundle.EMPTY);
	            try {
					startPetition();
					manageResponse();
				} catch (Exception e) {
					e.printStackTrace();
					if (petition != null){	
						bundle.putInt(Constants.PETITION_RESPONSE_CODE, petition.getResponseCode());		//Error code in bundle
					}else{
						bundle.putInt(Constants.PETITION_RESPONSE_CODE, Constants.NETWORK_EXCEPTION);				//Exception
					}receiver.send(Constants.STATUS_ERROR, bundle);
				}            
		 } else {												//ONLY USED FOR ALARMS(MemoryProvider)
				try {
					startPetitionWithoutResponse();
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	}
	
	/*
	 * Manages the response for the petition
	 */
	private void manageResponse(){
		if (petition != null){
	    	switch (petition.getResponseCode()) {
				case Constants.UNAUTHORIZED:			//ERROR 401
					restartPetitionByUauthorized();	            	
					break;
					
				case Constants.ACEPTED:					//OK 201
	        		receiver.send(Constants.STATUS_FINISHED, bundle);	//Bundle assigned in subclass
					break;
					
				case Constants.OK:						//OK 200
	        		receiver.send(Constants.STATUS_FINISHED, bundle);	//Bundle assigned in subclass
					break;
					
				default:								//FOR OTHERS...
					if (petition != null)	
						bundle.putInt(Constants.PETITION_RESPONSE_CODE, petition.getResponseCode());		//Error code in bundle
					else
						bundle.putInt(Constants.PETITION_RESPONSE_CODE, Constants.NETWORK_EXCEPTION);	
					receiver.send(Constants.STATUS_ERROR, bundle);
					break;
	
			}
		}else{
			bundle.putInt(Constants.PETITION_RESPONSE_CODE, Constants.NETWORK_EXCEPTION);
			receiver.send(Constants.STATUS_ERROR, bundle);
		}
	}

	/**
	 * Launchs the renew token, and after resstarst the original pet
	 */
	private void restartPetitionByUauthorized(){
		if (renewToken()){	//Renew token session because server responds 401
			try {
				startPetition();
				switch (petition.getResponseCode()) {				
					case Constants.ACEPTED:					//OK 201
		        		receiver.send(Constants.STATUS_FINISHED, bundle);	//Bundle assigned in subclass
						break;
						
					case Constants.OK:						//OK 200
		        		receiver.send(Constants.STATUS_FINISHED, bundle);	//Bundle assigned in subclass
						break;
						
					default:
		        		bundle.putInt(Constants.PETITION_RESPONSE_CODE, petition.getResponseCode());		//Error code in bundle
		                receiver.send(Constants.STATUS_ERROR, bundle);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (petition!=null)	
					bundle.putInt(Constants.PETITION_RESPONSE_CODE, petition.getResponseCode());		//Error code in bundle
				else
					bundle.putInt(Constants.PETITION_RESPONSE_CODE, Constants.NETWORK_EXCEPTION);				//Exception
				receiver.send(Constants.STATUS_ERROR, bundle);
			}				
		}else{
			bundle.putInt(Constants.PETITION_RESPONSE_CODE, Constants.NETWORK_EXCEPTION);
			receiver.send(Constants.STATUS_ERROR, bundle);
		}
		
	}

	/*
	 * Renew the token without calls GetSessionTokenWS
	 */
	private boolean renewToken() {
		return false;
	}
}