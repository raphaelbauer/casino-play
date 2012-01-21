package casino;

/**
 * 
 * Interface that must be implemented by user models.
 * 
 * You can use your own model and UserManager by setting Casino.CASINO_USER_MODEL_SIENA;
 * 
 * @author ra
 * 
 */
public interface CasinoUserManager {

	/**
	 * User creation.
	 * 
	 * If confirmation code is "" the user is considered as fully activated.
	 * 
	 * When confirmation code is set is has to be reset by deleteConfirmationCodeOfCasioUser
	 *  
	 * @param email
	 * @param passwordHash
	 * @param confirmationCode
	 */
	public void createNewCasinoUser(String email, String passwordHash,
			String confirmationCode);

	// /////////////////////////////////////////////////////////////////////////
	// Basic functions
	// /////////////////////////////////////////////////////////////////////////
	public boolean isUserActivated(String email);

	public boolean doesUserExist(String email);

	// /////////////////////////////////////////////////////////////////////////
	// Password management
	// /////////////////////////////////////////////////////////////////////////
	public String getUserPasswordHash(String email);

	public void setNewPasswordHashForUser(String email, String passwordHash);

	// /////////////////////////////////////////////////////////////////////////
	// Role management
	// Roles are normally simply Strings.
	// 
	// We are using eg. "admin"
	// /////////////////////////////////////////////////////////////////////////
	
	public boolean hasRole(String email, String role);

	public void addRole(String email, String role);

	public void removeRole(String email, String role);

	// /////////////////////////////////////////////////////////////////////////
	// Registration of user
	// A simple string. Sent to a user inside an email as link. User clicks that
	// link, comes back to the server and is recognized based on that
	// recoveryPasswordCode. Finally the user can reset his password.
	// /////////////////////////////////////////////////////////////////////////
	
	public String getCasinoUserWithRecoveryPasswordCode(
			String recoverPasswordCode);
		
	public void setRecoveryPasswordCode(String email,
			String recoveryPasswordCode);


	// /////////////////////////////////////////////////////////////////////////
	// Password recovery
	// /////////////////////////////////////////////////////////////////////////
	public String getCasinoUserWithConfirmationCode(String confirmationCode);

	public void deleteConfirmationCodeOfCasioUser(String email);

}
