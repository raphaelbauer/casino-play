package casino;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import play.Play;
import play.exceptions.ConfigurationException;

public class Casino {

	public static final String CASINO_USER_MODEL_MANAGER_SIENA = "models.casino.SienaUserManager";

	public static final String CASINO_USER_MODEL_MANAGER_JPA = "models.casino.jpa.JpaUserManager";

	private static CasinoUserManager CASINO_USER_MANAGER;

	public static final String CASINO_BCYPT_SALT_FACTOR = "casino.bcrypt_salt_factor";

	public static final String CASINO_BCYPT_SALT_FACTOR_DEFAULT = "10";

	static {
		initCasino();
	}

	/**
	 * Normally called automatically from inside a static block.
	 * 
	 * Loads and inits CasinoUserManager
	 * 
	 */
	public static void initCasino() {

		// using siena model by default...
		String casinoUserManagerString = Play.configuration
				.getProperty(CasinoApplicationConfConstants.CASINO_USER_MANAGER);

		if (casinoUserManagerString == null) {

			throw new ConfigurationException(
					"[Casino] Expect definition of "
					+ CasinoApplicationConfConstants.CASINO_USER_MANAGER
					+ " in application.conf\n" 
					+ "e.g.: "
					+ CasinoApplicationConfConstants.CASINO_USER_MANAGER + "="
					+ CASINO_USER_MODEL_MANAGER_JPA 
					+ "\nOr did you maybe forget casinojpa-x.x or casinosiena-x.x in your dependencies.yml?");
		}

		try {
			Class<CasinoUserManager> clazz = (Class<CasinoUserManager>) Class
					.forName(casinoUserManagerString);

			CASINO_USER_MANAGER = clazz.newInstance();

		} catch (Exception e) {
			throw new ConfigurationException(
					String.format(
							"Unable to create CasinoUserManager from application.conf: [%s]",
							e.getMessage()));
		}

	}

	public static String getHashForPassword(String password) {

		int saltFactor = Integer.parseInt(play.Play.configuration.getProperty(
				CASINO_BCYPT_SALT_FACTOR, CASINO_BCYPT_SALT_FACTOR_DEFAULT));

		return BCrypt.hashpw(password, BCrypt.gensalt(saltFactor));

	}

	public static boolean doPasswordAndHashMatch(String password,
			String passwordHash) {

		return BCrypt.checkpw(password, passwordHash);

	}

	/**
	 * called right after a user is executed...
	 */
	public static void executeAfterUserCreationHook(String email) {

		// using siena model by default...
		String afterCreationHookString = Play.configuration
				.getProperty(CasinoApplicationConfConstants.AFTER_CREATION_HOOK);

		if (afterCreationHookString != null) {

			if (!afterCreationHookString.equals("")) {

				try {

					Class<AfterUserCreationHook> clazz = (Class<AfterUserCreationHook>) Class
							.forName(afterCreationHookString);

					AfterUserCreationHook afterUserCreationHook = clazz
							.newInstance();
					afterUserCreationHook.execute(email);

				} catch (Exception e) {
					throw new ConfigurationException(
							String.format(
									"Unable to create AfterCreationHook from application.conf: [%s]",
									e.getMessage()));
				}
			}

		}

	}

	// ////////////////////////////////////////////////////////////////////////
	// Additional stuff:
	// ////////////////////////////////////////////////////////////////////////
	private static byte[] asByteArray(UUID uuid) {
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();
		byte[] buffer = new byte[16];

		for (int i = 0; i < 8; i++) {
			buffer[i] = (byte) (msb >>> 8 * (7 - i));
		}
		for (int i = 8; i < 16; i++) {
			buffer[i] = (byte) (lsb >>> 8 * (7 - i));
		}
		return buffer;
	}

	public static String shortUUID() {
		UUID uuid = UUID.randomUUID();
		return Base64.encodeBase64URLSafeString(asByteArray(uuid));
	}

	// /////////////////////////////////////////////////////////////////////////
	// convenience methods.
	// create new user also calles hook if there is one
	// /////////////////////////////////////////////////////////////////////////
	public static boolean createNewCasinoUser(String email,
			String passwordHash, String confirmationCode) {

		boolean userCreated = CASINO_USER_MANAGER.createNewCasinoUser(email,
				passwordHash, confirmationCode);

		// only exdc if user has been created...
		if (userCreated) {
			// and execute hook...
			executeAfterUserCreationHook(email);
		}

		return userCreated;

	}

	public static boolean isUserActivated(String email) {
		return CASINO_USER_MANAGER.isUserActivated(email);
	}

	public static boolean doesUserExist(String email) {
		return CASINO_USER_MANAGER.doesUserExist(email);
	}

	public static String getUserPasswordHash(String email) {
		return CASINO_USER_MANAGER.getUserPasswordHash(email);
	}

	public static void setNewPasswordHashForUser(String email,
			String passwordHash) {
		CASINO_USER_MANAGER.setNewPasswordHashForUser(email, passwordHash);

	}

	public static boolean hasRole(String email, String role) {
		return CASINO_USER_MANAGER.hasRole(email, role);
	}

	public static void addRole(String email, String role) {
		CASINO_USER_MANAGER.addRole(email, role);
	}

	public static void removeRole(String email, String role) {
		CASINO_USER_MANAGER.removeRole(email, role);

	}

	public static String getCasinoUserWithRecoveryPasswordCode(
			String recoverPasswordCode) {
		return CASINO_USER_MANAGER
				.getCasinoUserWithRecoveryPasswordCode(recoverPasswordCode);
	}

	public static void setRecoveryPasswordCode(String email,
			String recoveryPasswordCode) {

		CASINO_USER_MANAGER
				.setRecoveryPasswordCode(email, recoveryPasswordCode);

	}

	public static String getCasinoUserWithConfirmationCode(
			String confirmationCode) {
		return CASINO_USER_MANAGER
				.getCasinoUserWithConfirmationCode(confirmationCode);
	}

	public static void deleteConfirmationCodeOfCasioUser(String email) {
		CASINO_USER_MANAGER.deleteConfirmationCodeOfCasioUser(email);

	}

}
