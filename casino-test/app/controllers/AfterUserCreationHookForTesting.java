package controllers;

import org.junit.Test;

import play.cache.Cache;
import play.mvc.Http.Response;
import casino.AfterUserCreationHook;
import casino.Casino;

public class AfterUserCreationHookForTesting implements AfterUserCreationHook {

	public static String lastEmail = "";
	public static int executed = 0;

	@Override
	public void execute(String email) {

		lastEmail = email;
		executed++;

	}
}
