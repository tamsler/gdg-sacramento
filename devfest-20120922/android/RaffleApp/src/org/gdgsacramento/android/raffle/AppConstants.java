package org.gdgsacramento.android.raffle;


public interface AppConstants {

	public static final String LOG_TAG = "# RAFFLE_APP #";

	// HTTP REST ERROR INDICATOR
	public static final int NO_ERROR = 0;
	public static final int REST_ERROR = 1;

	// HTTP Client
	public static final int HTTP_CLIENT_CONNECTION_TIMEOUT = 20000; // 20
	public static final int HTTP_CLIENT_SO_TIMEOUT = 20000; // 20 seconds

	// HTTP Methods
	public static final int HTTP_GET = 0;
	public static final int HTTP_POST = 1;
	public static final int HTTP_DELETE = 2;
	public static final int HTTP_PUT = 4;

	// HTTP Content Type
	public static final String HTTP_CONTENT_TYPE_APPLICATION_JSON = "application/json";

	// HTTP Headers
	public static final String HEADER_AUTHORIZATION = "Authorization";
	public static final String HEADER_AUTHORIZATION_SCHEME_BASIC = "Basic ";
	public static final String HEADER_AUTHORIZATION_SCHEME_API_KEY = "ApiKey ";
	
	// REST HOSTs
	public static final String HOST = "http://raffle.aws.af.cm";
	
	// REST URLs
	public static final String POST_RAFFLE = HOST + "/api/v1/raffle";
	
	// JSON Keys
	public static final String RAFFLE_NAME = "raffle_name";

}
