package controllers.casino;

import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Util;

/**
 *
 */
public class SessionTransfer extends Controller {

	/** prefix for the cache key */
	public static final String PREFIX = "casino.SessionTransfer-";
	
	/**
	 * returns a token for usage in url.
	 * @param username
	 * @return
	 */
	@Util
	public static String doSetMemcacheToken(String username) {	
		
		String token = play.libs.Codec.UUID();		
		Cache.set(SessionTransfer.PREFIX + token, username, "1mn");		
		return token;
		
	}
	
	public static void loginViaToken(String token) {

		// set session on second server / url
		// most likely you 'll come from https://myserver.com and 
		// set the session at http://myserver.com
		String username = Cache.get(PREFIX + token, String.class);
		Cache.delete(PREFIX + token);

		if (username != null) {
			session.put("username", username);
		} else {
			forbidden();
		}
		
		// now as we are at the first server (where the request most likely started)
		// we check the flash cookie and redirect to the url that was set before
		String url = flash.get("url");
		if (url == null) {
			url = "/";
		}

		redirect(url);

	}

	public static void logoutViaToken(String token) {

		String username = Cache.get(PREFIX + token, String.class);
		Cache.delete(PREFIX + token);
		
		String connected = Security.connected();

		if (username != null) {
			
			if (connected.equals(username)) {
				session.clear();
			}
			
		} else {
			forbidden();
		}
		
		String url = flash.get("url");
		if (url == null) {
			url = "/";
		}

		redirect(url);

		redirect(url);

	}

}
