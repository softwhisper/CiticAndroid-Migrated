package curso.and24.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import curso.and24.shared.Constants;


public class JSONParser {

	public static Boolean parseSuccess(String jsonResponse){
		boolean isSuccess = false;
		try {
			JSONObject json = new JSONObject(jsonResponse);
			isSuccess = json.getBoolean(Constants.SUCCESS);
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}

	/**
	 * USED IN GETSESSIONTOKEN WS
	 * @param response
	 * @return
	 */
	public static String parseSessionToken(String response) {
		String token = null;
		try {
			JSONObject json = new JSONObject(response);			
			token = new String(json.getString(Constants.TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return token;
	}


	public static String parseMessage(String response) {
		String message = null;
		try {
			JSONObject json = new JSONObject(response);			
			message = new String(json.getString(Constants.MESSAGE));
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return message;
	}
	
	/*
	private static Offer parseOffer(JSONObject jOffer){
		Offer offer = new Offer();
		try{
			offer.setId(jOffer.getInt(Constants.ID));
			if (!jOffer.isNull(Constants.TITLE))
				offer.setTitle(jOffer.getString(Constants.TITLE));
			if (!jOffer.isNull(Constants.DESCRIPTION))
				offer.setDescription(jOffer.getString(Constants.DESCRIPTION));	
			if (!jOffer.isNull(Constants.NUMBER_OF_CHECKINS))
				offer.setNumberOfCheckins(jOffer.getInt(Constants.NUMBER_OF_CHECKINS));
			if (!jOffer.isNull(Constants.NUMBER_OF_USERS))
				offer.setNumberOfUsers(jOffer.getInt(Constants.NUMBER_OF_USERS));
			if (!jOffer.isNull(Constants.OFFER_TYPE_ID)){
				offer.setOfferTypeId(jOffer.getInt(Constants.OFFER_TYPE_ID));
			}
		}catch (JSONException je) {
			je.printStackTrace();
			return null;
		}
		return offer;
	}
	
	private static OfferInfo parseOfferInfo(JSONObject jOfferInfo){
		OfferInfo offerInfo = new OfferInfo();
		try{
			offerInfo.setId(jOfferInfo.getInt(Constants.ID));
			if (!jOfferInfo.isNull(Constants.TITLE))
				offerInfo.setTitle(jOfferInfo.getString(Constants.TITLE));
			if (!jOfferInfo.isNull(Constants.DESCRIPTION))
				offerInfo.setDescription(jOfferInfo.getString(Constants.DESCRIPTION));
			if (!jOfferInfo.isNull(Constants.IMAGE_URL))
				offerInfo.setImg(jOfferInfo.getString(Constants.IMAGE_URL));	
			if (!jOfferInfo.isNull(Constants.NUMBER_OF_CHECKINS))
				offerInfo.setNumberOfCheckins(jOfferInfo.getInt(Constants.NUMBER_OF_CHECKINS));
			if (!jOfferInfo.isNull(Constants.NUMBER_OF_USERS))
				offerInfo.setNumberOfUsers(jOfferInfo.getInt(Constants.NUMBER_OF_USERS));
		}catch (JSONException je) {
			je.printStackTrace();
			return null;
		}
		return offerInfo;
	}
	*/
}
