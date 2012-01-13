package controllers.casino;

import models.casino.User;

import org.junit.Test;

import play.modules.siena.SienaFixtures;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class SecurityCasinoTest extends FunctionalTest {

	
	@Test
	public void testCheckMethod() {
		
		SienaFixtures.deleteAllModels();
		clearCookies();
		
		User user = new User("user@me.com", "secret_password");
		user.confirmationCode = "";
		user.save();
		
		
		//check that access is forbidden when no role:
		Response response = GET("/secure/admin_only");
		assertStatus(302, response);
		
		response = GET("/secure/superadmin_only");
		assertStatus(302, response);
		
		//add role "admin"
		user.addRole("admin");
		user.save();
		
		
		//and login...
		Helper.doLogin("user@me.com", "secret_password");
		
//		//check access again
//		response = GET("/secure/admin_only");
//		assertIsOk(response);
//		
//		response = GET("/secure/superadmin_only");
//		assertStatus(403, response);
//		
//		
//		
//		
//		//add role "superadmin"
//		user.addRole("superadmin");
//		user.save();
//		
//		//check access again
//		response = GET("/secure/admin_only");
//		assertIsOk(response);
//		
//		response = GET("/secure/superadmin_only");
//		assertIsOk(response);
//		
//		
//		//and remove role "superadmin"
//		user.removeRole("superadmin");
//		user.save();
//		
//		//check access again
//		response = GET("/secure/admin_only");
//		assertIsOk(response);
//		
//		response = GET("/secure/superadmin_only");
//		assertStatus(403, response);
		
		
	}

}
