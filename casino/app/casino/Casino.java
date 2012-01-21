package casino;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import play.Play;
import play.exceptions.ConfigurationException;

public class Casino {

	public static final String CASINO_USER_MODEL = "casino.user";

	public static final String CASINO_USER_MODEL_SIENA = "models.casino.UserManager";

	private static CasinoUserManager CASINO_USER;
	
	
	public static final String CASINO_BCYPT_SALT_FACTOR = "casino.bcrypt_salt_factor";
	
	public static final String CASINO_BCYPT_SALT_FACTOR_DEFAULT = "10";

	static {

		// using siena model by default...
		String casinoUserModelString = Play.configuration.getProperty(
				CASINO_USER_MODEL, CASINO_USER_MODEL_SIENA);

		try {
			Class<CasinoUserManager> clazz = (Class<CasinoUserManager>) Class
					.forName(casinoUserModelString);

			CASINO_USER = clazz.newInstance();

		} catch (Exception e) {
			throw new ConfigurationException(String.format(
					"Unable to create CasinoUser instance: [%s]",
					e.getMessage()));
		}
	}
	
	
	public static CasinoUserManager getCasinoUser() {
		
		return CASINO_USER;
		
	}
	
	
	public static String getHashForPassword(String password) {
		
		int saltFactor = Integer.parseInt(play.Play.configuration.getProperty(
				CASINO_BCYPT_SALT_FACTOR, CASINO_BCYPT_SALT_FACTOR_DEFAULT));
		
		return BCrypt.hashpw(password, BCrypt.gensalt(saltFactor));
		
		
	}
	
	public static boolean doPasswordAndHashMatch(String password, String passwordHash) {
		
		return BCrypt.checkpw(password, passwordHash);
		
	}
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////
	// Additional stuff:
	//////////////////////////////////////////////////////////////////////////
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
	

}
