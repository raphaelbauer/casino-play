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
	
	public static void loginViaToken(String token, String url) {

		String username = Cache.get(PREFIX + token, String.class);
		Cache.delete(PREFIX + token);

		if (username != null) {
			session.put("username", username);
		} else {
			forbidden();
		}

		redirect(url);

	}

	public static void logoutViaToken(String token, String url) {

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

		redirect(url);

	}

}
