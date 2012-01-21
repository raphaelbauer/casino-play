package casino;

/**
 * Used in application.conf
 * @author ra
 *
 */
public interface CasinoApplicationConfConstants {
	
	/* must be set in application conf */
	public static String EMAIL_FROM = "casino.emailFrom";
	
	/* optional in application conf */
	public static String SECURE_URL = "casino.secureUrl";
	
	/* optional in application conf */
	public static String REGULAR_URL = "casino.regularUrl";
	
	/**
	 * must implement AfterUserCreationHook interface...
	 */
	public static String AFTER_CREATION_HOOK = "casino.afterUserCreationHook";
	
	public static final String CASINO_USER_MANAGER = "casino.userManager";

}
