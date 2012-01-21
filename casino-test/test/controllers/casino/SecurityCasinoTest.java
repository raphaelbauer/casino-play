package controllers.casino;

import org.junit.Test;

import play.Play;
import play.cache.Cache;
import play.modules.siena.SienaFixtures;
import play.mvc.Http.Response;
import play.mvc.Util;
import play.test.Fixtures;
import casino.Casino;
import casino.CasinoApplicationConfConstants;

public class SecurityCasinoTest extends BaseFunctionalTest {

	@Test
	public void testEveryThing() {

		///////////////////////////////////////////////////////////////////////
		//test default config...
		///////////////////////////////////////////////////////////////////////
		testRoundTrip();
		
		System.out.println("first roundtrip run...");
		
		///////////////////////////////////////////////////////////////////////
		//explicitly test Siena
		///////////////////////////////////////////////////////////////////////
		doSetup();
		
		
		initDefaultCasinoJpaConfiguration();
		
		testRoundTrip();
		
		System.out.println("jpa roundtrip run...");

		
		
		///////////////////////////////////////////////////////////////////////
		//explicitly test Siena
		///////////////////////////////////////////////////////////////////////
		doSetup();

		
		initDefaultCasinoSienaConfiguration();
		
		testRoundTrip();
		
		
		System.out.println("siena roundtrip run...");
		
		///////////////////////////////////////////////////////////////////////
		//explicitly test that exception is thrown when stuff goes wrong:
		///////////////////////////////////////////////////////////////////////
		doSetup();
		
		
		boolean gotException = false;
		Play.configuration.setProperty(
				CasinoApplicationConfConstants.CASINO_USER_MANAGER,
				"com.me.WROOOOOONG_CONFIGURATION");
		
		try {
			Casino.initCasino();
		} catch (Exception e) {
			gotException = true;
		}
		
		assertTrue(gotException);
		
		
		
		
	}

	
	public void testRoundTrip() {

		String email = "user@me.com";

		String password = "password";

		String passwordHash = Casino.getHashForPassword(password);

		Casino.createNewCasinoUser(email, passwordHash, "");

		// check that access is forbidden when no role:
		Response response = GET("/secure/admin_only");
		assertStatus(302, response);

		response = GET("/secure/superadmin_only");
		assertStatus(302, response);

		// add role "admin"
		Casino.addRole(email, "admin");

		assertTrue(Casino.hasRole(email, "admin"));

		clearCookies();
		Cache.clear();
		// and login...
		Helper.doLogin(email, password);

		// check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);

		response = GET("/secure/superadmin_only");
		assertStatus(403, response);

		// add role "superadmin"
		Casino.addRole(email, "superadmin");

		// check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);

		response = GET("/secure/superadmin_only");
		assertIsOk(response);

		// and remove role "superadmin"
		Casino.removeRole(email, "superadmin");

		// check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);

		response = GET("/secure/superadmin_only");
		assertStatus(403, response);

	}

}
