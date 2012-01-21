package models.casino;

import casino.Casino;
import casino.CasinoUserManager;

public class SienaUserManager implements CasinoUserManager {

	@Override
	public void createNewCasinoUser(String email, String passwordHash,
			String confirmationCode) {

		User user = new User(email, passwordHash, confirmationCode);
		user.save();

	}

	@Override
	public boolean isUserActivated(String email) {

		User user = User.all().filter("email", email).get();

		// the email should be there
		if (user == null) {
			return false;
		}

		// make sure the user confirmed the name
		if (user.confirmationCode.length() != 0) {
			return false;
		}

		return true;
	}

	@Override
	public boolean hasRole(String email, String role) {

		User user = User.all().filter("email", email).get();

		if (user == null) {
			return false;
		}

		return user.hasRole(role);

	}

	@Override
	public void addRole(String email, String role) {

		User user = User.all().filter("email", email).get();
		
		if (user == null) {
			return;
		}
		
		
		user.addRole(role);
		user.save();

	}

	@Override
	public void removeRole(String email, String role) {

		User user = User.all().filter("email", email).get();

		if (user == null) {
			return;
		}
		

			user.removeRole(role);
			user.save();
		
	}

	@Override
	public void setRecoveryPasswordCode(String email,
			String recoveryPasswordCode) {

		User user = User.all().filter("email", email).get();

		if (user == null) {
			return;
		}

		user.recoverPasswordCode = recoveryPasswordCode;
		user.save();

	}

	@Override
	public String getCasinoUserWithConfirmationCode(String confirmationCode) {

		User user = User.all().filter("confirmationCode", confirmationCode)
				.get();

		if (user == null) {
			return null;
		}

		return user.email;

	}

	@Override
	public void deleteConfirmationCodeOfCasioUser(String email) {

		User user = User.all().filter("email", email).get();

		if (user == null) {
			return;
		}
		
		user.confirmationCode = "";
		user.save();

	}

	@Override
	public String getUserPasswordHash(String email) {

		User user = User.all().filter("email", email).get();

		if (user == null) {
			return null;
		}

		return user.pwHash;
	}

	@Override
	public void setNewPasswordHashForUser(String email, String passwordHash) {

		User user = User.all().filter("email", email).get();

		if (user == null) {
			return;
		}

		user.pwHash = passwordHash;

		user.save();

	}

	@Override
	public boolean doesUserExist(String email) {

		User user = User.all().filter("email", email).get();

		if (user == null) {
			return false;
		}

		return true;
	}

	@Override
	public String getCasinoUserWithRecoveryPasswordCode(
			String recoverPasswordCode) {

		User user = User.all()
				.filter("recoverPasswordCode", recoverPasswordCode).get();

		if (user == null) {
			return null;
		}

		return user.email;
	}

}
