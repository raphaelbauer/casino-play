package controllers.casino;

import org.junit.Before;
import org.junit.Test;

import play.Play;
import play.cache.Cache;
import play.modules.siena.SienaFixtures;
import play.test.Fixtures;
import play.test.FunctionalTest;
import casino.CasinoApplicationConfConstants;

public class BaseFunctionalTest extends FunctionalTest {

	@Before
	public void doSetup() {

		//reset all stuff...
		Fixtures.deleteAllModels();
		SienaFixtures.deleteAllModels();
		
		clearCookies();
		Cache.clear();

		Play.configuration.setProperty(CasinoApplicationConfConstants.SECURE_URL, "");
		Play.configuration.setProperty(CasinoApplicationConfConstants.REGULAR_URL, "");

	}

	@Test
	public void dummy() {
		// intentionally left empty... we just use the doSetupMethod
	}

}
