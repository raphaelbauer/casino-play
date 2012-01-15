package controllers.casino;

import java.util.List;

import models.casino.User;

import org.junit.Test;

import controllers.Users;

import play.Play;
import play.cache.Cache;
import play.modules.siena.SienaFixtures;
import play.mvc.Http.Response;
import play.test.Fixtures;

public class SecurityCasinoTest extends BaseFunctionalTest {

	
	@Test
	public void testCheckMethod() {
		

		
		User user = new User("user@me.com", "secret_password");
		user.confirmationCode = "";
		user.save();
		
		List<User> users = User.all().fetch();
		System.out.println("num of users: " + users.size());
		
		//check that access is forbidden when no role:
		Response response = GET("/secure/admin_only");
		assertStatus(302, response);
		
		response = GET("/secure/superadmin_only");
		assertStatus(302, response);
		
		//add role "admin"
		user.addRole("admin");
		user.save();
		
		
		users = User.all().fetch();
		System.out.println("num of users: " + users.size());
		for (User user1 : users) {
			
			System.out.println("user: " + user1.email);
			System.out.println("user has role admin: " + user1.hasRole("admin"));
			
		}
		
		
		clearCookies();
		Cache.clear();
		//and login...
		Helper.doLogin("user@me.com", "secret_password");
		
		//check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);
		
		response = GET("/secure/superadmin_only");
		assertStatus(403, response);
		
		
		
		
		//add role "superadmin"
		user.addRole("superadmin");
		user.save();
		
		//check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);
		
		response = GET("/secure/superadmin_only");
		assertIsOk(response);
		
		
		//and remove role "superadmin"
		user.removeRole("superadmin");
		user.save();
		
		//check access again
		response = GET("/secure/admin_only");
		assertIsOk(response);
		
		response = GET("/secure/superadmin_only");
		assertStatus(403, response);
		
		
	}
	
}
