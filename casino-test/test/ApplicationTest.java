import org.junit.Test;

import controllers.casino.BaseFunctionalTest;

import play.Play;
import play.mvc.Http.Response;

public class ApplicationTest extends BaseFunctionalTest {


	@Test
	public void testSiginingOfTwoDomainsWorks() {

		// assumption. All play tests are running on http://localhost:9000
		// Therefore we check if a redirect to http://127.0.0.1:9000 is enforced
		// Scenario equals a http://example.com/login being redirected to
		// https://example.com

		// scenario 1: no fixed url configuration property is set...
		Play.configuration.setProperty("casino.secureUrl", "");
		Play.configuration.setProperty("casino.regularUrl", "");

		
		//Test GET
		Response response = GET("/login");
		assertStatus(200, response);

		response = GET("/logout");
		assertStatus(302, response); //redirect => but on same server:
		assertTrue(response.getHeader("Location").equals("/login"));

		response = GET("/registration", true);
		assertStatus(200, response);

		response = GET("/registration/pending", true);
		assertStatus(200, response);

		response = GET("/registration/confirm/CODE", true);
		assertStatus(200, response);

		response = GET("/registration/lostpassword", true);
		assertStatus(200, response);

		response = GET("/registration/lostpassword/pending", true);
		assertStatus(200, response);

		response = GET("/registration/lostpassword/confirm/CODE", true);
		assertStatus(200, response);

		
		
		// scenario 2: now set fixed url => check that redirect is done on all:
		Play.configuration.setProperty("casino.fixedUrl",
				"http://127.0.0.1:9000");
		
		Play.configuration.setProperty("casino.secureUrl", "http://127.0.0.1:9000");
		Play.configuration.setProperty("casino.regularUrl", "http://localhost:9000");
		
		//Test GET
		response = GET("/login");
		assertStatus(302, response);
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));

		response = GET("/logout");
		assertStatus(302, response); //redirect
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));

		response = GET("/registration", true);
		assertStatus(302, response);
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));

		response = GET("/registration/pending", true);
		assertStatus(302, response);
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));

		response = GET("/registration/confirm/CODE", true);
		assertStatus(302, response);
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));

		response = GET("/registration/lostpassword", true);
		assertStatus(302, response);
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));

		response = GET("/registration/lostpassword/pending", true);
		assertStatus(302, response);
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));

		response = GET("/registration/lostpassword/confirm/CODE", true);
		assertStatus(302, response);
		assertTrue(response.getHeader("Location").startsWith("http://127.0.0.1:9000"));


	}

}