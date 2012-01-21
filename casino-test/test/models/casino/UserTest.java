package models.casino;
import org.junit.Before;
import org.junit.Test;

import play.modules.siena.SienaFixtures;
import play.test.UnitTest;
import casino.Casino;


public class UserTest extends UnitTest {

	@Before
	public void doInit() {
		
		SienaFixtures.deleteAllModels();
		
		
	}
	
	@Test
	public void testUserModel() {
		
		String hash = Casino.getHashForPassword("password");
		
		User user = new User("me@email.com", hash, "CODE");
		
		
		assertTrue(user.email.equals("me@email.com"));
		
		
		// confirmation code must be set at the beginning..
		// => this account is awaiting confirmation...
		assertFalse(user.confirmationCode.equals(""));
		
		//confirm that hash is computed correctly
		assertTrue(user.pwHash.startsWith("$2a$10$"));
		
		
		
		
		//test role based management:
		user.addRole("superadmin");
		user.addRole("admin");
		
		assertTrue(user.hasRole("admin"));
		assertTrue(user.hasRole("superadmin"));
		
		user.removeRole("superadmin");
		assertFalse(user.hasRole("superadmin"));
		
		user.removeRole("admin");
		assertFalse(user.hasRole("admin"));
		
		
	}
}

