package controllers.casino;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import play.mvc.Http;
import play.mvc.Http.Response;
import play.mvc.Util;
import play.test.FunctionalTest;

public class Helper extends FunctionalTest {
	
	
	@Test
	public void dummyTest() {
		
		//does nothing intentionally...
	}
	
	@Util
	public static Map<String, Http.Cookie> doLogin(String email, String password) {
		
		String postUrl = "/login";
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", email);
		map.put("password", password);
		map.put("remember", "true");
		
		
		Response post = POST(postUrl, map);
		Map<String, Http.Cookie> cookies = post.cookies;
		
		return cookies;
		
		
	}

}
