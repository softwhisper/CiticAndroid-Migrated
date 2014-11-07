package curso.and24.shared;

public interface Constants {
	// RECEIVER CTS
	public final static int WS_AVALIABLE = 0;
	public final static int WS_RUNNING = 1;
	public final static int WS_GET_USER = 2;
	public final static int WS_TOKEN = 3;
	public final static int WS_VALIDATE_CODE_AND_PHONE = 4;
	public final static int WS_SIGN_UP = 5;
	public final static int WS_GET_AREAS = 6;
	public final static int WS_GET_NEAREST_OFFER = 7;
	public static final int WS_GET_AREA_OFFERS_BY_ID = 8;

	
	public static final int READER_QR_RESTART_TIME = 5000;
	
	//PROGRESS DIALOG
	public static final int DIALOG_WITH_MESSASGE = 0;
	public static final int DIALOG_WITHOUTS_MESSAGE = 1;

	// WS STATE
	public static final int STATUS_RUNNING = 0;
	public static final int STATUS_FINISHED = 1;
	public static final int STATUS_ERROR = 2;
	public static final String ERRNO_NO_CONNECTION = "No connection avaliable";

	// REST
	public static final String PETITION_RESPONSE_CODE = "petition_response_code";
	
	// CRASH REPORTS
	public static final String PARAM_DATE = "crash_report[error_date]";
	public static final String PARAM_TABLET_ID = "crash_report[tablet_id]";
	public static final String PARAM_CRASH = "crash_report[stack_trace]";
	public static final String PARAM_APP_VERSION_CODE = "crash_report[version]";

	// WS URL'S
	public static final String CRASH_REPORTS_BASE_URL = "/api/crash_reports/";
	public static final String GET_SESION_TOKEN_BASE_URL = "/api/api_sessions/";
	public static final String GET_OFFER_INFO_BASE_URL = "/api/shops/get_offer_info/"; // /api/shops/get_offer_info/:id
	public static final String NEAREST_SHOPS_BASE_URL = "/api/shops/nearest_shops/"; // /api/shops/nearest_shops/:lat/:long
	public static final String LOGIN_BASE_URL = "/api/users/login/"; // /api/users/login/:qr_code
	public static final String GET_USER_INFO_BASE_URL = "/api/users/get_user_info"; // /api/users/get_user_info?id=1
	public static final String UPDATE_USER_INFO_BASE_URL = "/api/users/update_user_info"; // /api/users/get_user_info?id=1
	public static final String GET_USER_COUPONS_BASE_URL = "/api/users/get_user_coupons/"; // /api/users/get_user_coupons/:user_account_id
	public static final String IMAGE_QR_BASE_URL = "/qr_codes/";	// /qr_codes/id/image.png
	public static final String IMAGE_QR_NAME = "/image.png";
	public static final String CREATE_USER_BASE_URL = "/api/users/mobile_sign_up";
	public static final String VALIDATE_SMS_BASE_URL = "/api/users/validate_code_and_phone";
	public static final String DELIVER_SMS_BASE_URL = "/api/users/deliver_sms";
	public static final String RENEW_QRCODE_BASE_URL = "/api/users/renew_code";
	public static final String GET_AREAS_URL = "/api/areas/all";//id y nombre returna
	public static final String GET_AREA_OFFERS = "/api/areas/get_offers_by_id/";//:id como parametro
	
	// PARSER AND SERVICES CONSTANTS
	public static final String SHOPPER_IMAGE = "logo_url";
	public static final String SHOP = "shop";
	public static final String COMMAND = "command";
	public static final String SUCCESS = "success";
	public static final String ID = "id";
	public static final String ID_SHOP = "id_shop";
	public static final String MESSAGE = "message";
	public static final String QRCODE = "qr_code";
	public static final String ACTIVE_QR = "qr";
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String STARTS_AT = "starts_at";
	public static final String ENDS_AT = "ends_at";
	public static final String USER_PROFILE = "user_profile";
	public static final String SURNAME = "surname";
	public static final String EMAIL = "email";
	public static final String CIF = "cif";
	public static final String PHONE_NUMBER = "phone_number";
	public static final String LANG = "lang";
	public static final String BIRTHDAY = "birthday";
	public static final String LASTNAME = "lastname";
	public static final String USER_NAME = "user_name";
	public static final String USER_ID = "user_id";
	public static final String COUPONS = "coupons";
	public static final String SYSTEM_OFFERS = "system_offers";
	public static final String OFFER = "offer";
	public static final String OFFERS = "offers";
	public static final String ACTIVE_OFFERS = "active_offers";
	public static final String NUM_PRICES = "num_premios";
	public static final String NUM_NEW_PROMOS = "num_new_promos";
	public static final String NUM_TOTAL_PROMOS = "num_total_promos";
	public static final String LANGUAGE = "language";
	public static final String LANGUAGE_ID = "language_id";
	public static final String SMS_CODE = "sms_code";
	public static final String CODE = "code";
	public static final String IMAGE_URL = "image_url";
	public static final String CHECKINS = "checkins";
	public static final String EXPIRATION_DATE = "expiration_date";
	public static final String OFFER_ID = "offer_id";
	public static final String STATE = "state";
	public static final String IS_RAFFLE = "is_raffle";
	public static final String STATE_COMPLETE = "completed";
	public static final String STATE_INPROGRESS = "in_progress";
	public static final String NUMBER_OF_CHECKINS = "number_of_checkins";
	public static final String NUMBER_OF_ACTIONS = "number_of_actions";
	public static final String NUMBER_OF_COMPLETED_COUPONS = "num_of_compleated_coupons";
	public static final String NUMBER_OF_USERS = "number_of_users";
	public static final String COUPON_TYPE = "coupon_type";
	public static final String APP_VERSION = "app_version";
	public static final String APP_URL = "app_url";
	public static final String COUPON_ID = "coupon_id";
	public static final String SHOPKEEPER_CODE = "shopkeeper_code";
	public static final String COMPLETED_COUPONS_ID = "completed_coupons_ids";
	public static final String QRS_FRIENDS = "qrs_string";
	public static final String OFFER_INFO = "offer_info";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String LOGO = "logo";
	public static final String USER = "user";
	public static final String COUNTRY = "country";
	public static final String POSTAL_CODE = "postal_code";
	public static final String SEX = "sex";
	public static final String PROMOS = "promos";
	public static final String COUPON = "coupon";
	public static final String DATA = "data";
	public static final String ITEM_CLICKED_ID = "id";
	public static final String OFFER_TYPE_ID = "offer_type_id";
	public static final String WEB = "web";
	public static final String BACK_ENABLE = "back_enable";
	public static final String GALLERY_URLS = "gallery_urls";
	public static final String PRIZES = "number_of_redeem_coupons";
	public static final String CHECKINS_THIS_WEEK = "total_weekly_checkins";
	public static final String CHECKINS_TOTAL = "total_checkins";
	public static final String COUPONS_NUMBER = "total_coupons";
	public static final String AREAS = "areas";
	public static final String NUMBER_OF_DONE_ACTIONS = "number_of_done_actions";

	

	// ws mobile send confirmation post params
	public static final String USER_ACCOUNT_NAME = "user_account[user_profile_attributes][name]";
	public static final String USER_ACCOUNT_BIRTHDAY = "user_account[user_profile_attributes][birthday]";
	public static final String USER_ACCOUNT_EMAIL = "user_account[email]";
	public static final String USER_ACCOUNT_SURNAME = "user_account[user_profile_attributes][lastname]";
	public static final String USER_ACCOUNT_PHONE = "user_account[phone_number]";
	public static final String USER_ACCOUNT_LANG = "user_account[user_profile_attributes][language_id]";

	public static final String SHOPS_ARRAY = "shops";
	public static final String SHOP_SELECTED = "shop";

	public static final String THUMB = "thumb";

	public static final String URL = "url";

	// get session token ws
	public static final String AUTHORIZATION = "Authorization";
	public static final String RANDOM = "random";
	public static final String TOKEN = "token";

	// TABLET CONFIG
	public static final String TABLET_CONFIG = "tablet_configurator";
	public static final String BASE_URL = "base_url";
	public static final String LOGIN_TIME = "login_time";
	public static final String OFFER_ROTATION_TIME = "offer_rotation_time";
	public static final String TOKEN_EXPIRATION_TIME = "token_expiration_time";
	public static final String TTL = "ttl";
	public static final String VERSION = "version";
	public static final String CONFIG_TIME = "tablet_configurator_time";

	// HTTP CODES
	public static final int UNAUTHORIZED = 401;
	public static final int OK = 200;
	public static final int ACEPTED = 201;

	// ERROR CODES FOR MANAGE PETITIONS
	public static final int NETWORK_EXCEPTION = 0;

	// VARIOUS
	public static final String SLASH = "/";
	public static final int EMPTY = -1;
	public static int ZERO = 0;
	public static final int FALSE = 0;
	public static final int TRUE = 1;
	public static final String PREFS_NAME = "prefs_feiron_mobile";
	public static final int INFORMATIVE_OFFER = 1;
	public static final int IS_MAN = 1;
	public static final int IS_WOMAN = 0;



	// COUPON TYPES
	public static final String MULTIPLE_USER = "multiple_users";
	public static final String MULTIPLE_CHECKIN = "multiple_checkins";
	public static final String SINGLE = "single";
	public static final String INFORMATIVE = "informative";
	public static final String REGISTRY_CHECKIN = "registry_checkin";


}

