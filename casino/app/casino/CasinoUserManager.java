package casino;

/**
 * 
 * Interface that must be implemented by custom user models.
 * 
 * 
 * 
 * @author ra
 * 
 */
public interface CasinoUserManager {

	// /////////////////////////////////////////////////////////////////////////
	// User creation
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * Create a new user in db.
	 * 
	 * Also requires that a confirmation code is set as long as user is not
	 * authenticated.
	 * 
	 * 
	 * @param email
	 * @param password
	 * @param confirmationCode
	 */
	public void createNewCasinoUser(String email, String passwordHash,
			String confirmationCode);

	// /////////////////////////////////////////////////////////////////////////
	// Basic functions
	// /////////////////////////////////////////////////////////////////////////
	/**
	 * important: user should not have a confirmation code...
	 * 
	 * @param email
	 * @return
	 */
	public boolean isUserActivated(String email);

	public boolean doesUserExist(String email);

	// /////////////////////////////////////////////////////////////////////////
	// Password management
	// /////////////////////////////////////////////////////////////////////////
	public String getUserPasswordHash(String email);

	public void setNewPasswordHashForUser(String email, String passwordHash);

	// /////////////////////////////////////////////////////////////////////////
	// Role management
	// /////////////////////////////////////////////////////////////////////////
	
	public boolean hasRole(String email, String role);
	
	
	/**
	 * Roles are normally simply Strings.
	 * 
	 * We are using eg. "admin"
	 * 
	 * @param email
	 * @param role
	 */
	public void addRole(String email, String role);

	public void removeRole(String email, String role);

	// /////////////////////////////////////////////////////////////////////////
	// Registration of user
	// /////////////////////////////////////////////////////////////////////////
	
	public String getCasinoUserWithRecoveryPasswordCode(
			String recoverPasswordCode);
		
	/**
	 * A simple string. Sent to a user inside an email as link. User clicks that
	 * link, comes back to the server and is recognized based on that
	 * recoveryPasswordCode. Finally the user can reset his password.
	 * 
	 * @param email
	 * @param revoceryPasswordCode
	 */
	public void setRecoveryPasswordCode(String email,
			String recoveryPasswordCode);


	// /////////////////////////////////////////////////////////////////////////
	// Password recovery
	// /////////////////////////////////////////////////////////////////////////
	public String getCasinoUserWithConfirmationCode(String confirmationCode);

	public void deleteConfirmationCodeOfCasioUser(String email);

}
