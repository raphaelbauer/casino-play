package controllers.casino;

import org.junit.Test;

import play.cache.Cache;
import play.mvc.Http.Response;
import casino.Casino;

public class SecurityCasinoTest extends BaseFunctionalTest {

	
	@Test
	public void testCheckMethod() {
		
		String email = "user@me.com";
		
		String password = "password";
		
		String passwordHash = Casino.getHashForPassword(password);
		
		Casino.createNewCasinoUser(email, passwordHash, "");

		
		//check that access is forbidden when no role:
		Response response = GET("/secure/admin_only");
		assertStatus(302, response);
		
		response = GET("/secure/superadmin_only");
		assertStatus(302, response);
		
		//add role "admin"
		Casino.addRole(email, "admin");
		
		assertTrue(Casino.hasRole(email, "admin"));

		
		clearCookies();
		Cache.clear();
		//and login...
		Helper.doLogin(email, password);
		
		//check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);
		
		response = GET("/secure/superadmin_only");
		assertStatus(403, response);
		
		
		
		
		//add role "superadmin"
		Casino.addRole(email, "superadmin");
		
		//check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);
		
		response = GET("/secure/superadmin_only");
		assertIsOk(response);
		
		
		//and remove role "superadmin"
		Casino.removeRole(email, "superadmin");

		
		//check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);
		
		response = GET("/secure/superadmin_only");
		assertStatus(403, response);
		
		
	}
	
}
