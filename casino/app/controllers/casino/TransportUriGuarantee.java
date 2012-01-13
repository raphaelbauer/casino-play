package controllers.casino;

import play.Play;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Util;

public class TransportUriGuarantee extends Controller {

	/**
	 * Scenario:
	 * 
	 * You want to force user to use https instead of http for login (and maybe other critical stuff).
	 */
	@Before
	public static void redirectToFixedUrlWhenSet() {
		
		redirectIfNecessary();
		
	}
	
	/** 
	 * 
	 * also used in login logout facade :)
	 * 
	 */
	@Util
	public static void redirectIfNecessary() {
		
		String secureUrl = Play.configuration.getProperty(
				CasinoConstants.secureUrl, "");

		
		//is the parameter set in application.conf?
		if (!secureUrl.equals("")) {
			
			//parameter set => now redirect when we are not on that server:
			if (!request.getBase().equals(secureUrl)) {
				redirect(secureUrl + request.path);
			}
			
		}
		
	}
	
}
