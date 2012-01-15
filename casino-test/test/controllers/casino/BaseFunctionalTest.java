package controllers.casino;

import org.junit.Before;
import org.junit.Test;

import play.Play;
import play.cache.Cache;
import play.modules.siena.SienaFixtures;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class BaseFunctionalTest extends FunctionalTest {

	@Before
	public void doSetup() {

		//reset all stuff...
		Fixtures.deleteAllModels();
		SienaFixtures.deleteAllModels();
		
		clearCookies();
		Cache.clear();

		Play.configuration.setProperty(CasinoConstants.secureUrl, "");
		Play.configuration.setProperty(CasinoConstants.regularUrl, "");

	}

	@Test
	public void dummy() {
		// intentionally left empty... we just use the doSetupMethod
	}

}
