package controllers.casino;

import org.junit.Before;
import org.junit.Test;

import play.Play;
import play.cache.Cache;
import play.modules.siena.SienaFixtures;
import play.mvc.Util;
import play.test.Fixtures;
import play.test.FunctionalTest;
import casino.Casino;
import casino.CasinoApplicationConfConstants;

public class BaseFunctionalTest extends FunctionalTest {

	@Before
	public void doSetup() {

		// reset all stuff...
		Fixtures.deleteAllModels();
		SienaFixtures.deleteAllModels();

		clearCookies();
		Cache.clear();

		Play.configuration.setProperty(
				CasinoApplicationConfConstants.SECURE_URL, "");
		Play.configuration.setProperty(
				CasinoApplicationConfConstants.REGULAR_URL, "");

		initDefaultCasinoSienaConfiguration();

	}

	@Util
	public void initDefaultCasinoSienaConfiguration() {

		Play.configuration.setProperty(
				CasinoApplicationConfConstants.CASINO_USER_MANAGER,
				Casino.CASINO_USER_MODEL_MANAGER_SIENA);
		
		Casino.initCasino();

	}

	@Util
	public void initDefaultCasinoJpaConfiguration() {

		Play.configuration.setProperty(
				CasinoApplicationConfConstants.CASINO_USER_MANAGER,
				Casino.CASINO_USER_MODEL_MANAGER_JPA);
		
		Casino.initCasino();

	}	

	@Test
	public void dummy() {
		// intentionally left empty... we just use the doSetupMethod
	}

}
