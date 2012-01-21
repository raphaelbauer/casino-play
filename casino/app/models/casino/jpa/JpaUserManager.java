package models.casino.jpa;

import java.util.List;

import play.Logger;

import casino.Casino;
import casino.CasinoUserManager;

public class JpaUserManager implements CasinoUserManager {

	@Override
	public void createNewCasinoUser(String email, String passwordHash,
			String confirmationCode) {

		User user = new User(email, passwordHash, confirmationCode);
		user.save();

	}

	@Override
	public boolean isUserActivated(String email) {

		User user = getOneUserWithEmail(email);

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

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return false;
		}

		return user.hasRole(role);

	}

	@Override
	public void addRole(String email, String role) {

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return;
		}

		user.addRole(role);
		user.save();

	}

	@Override
	public void removeRole(String email, String role) {

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return;
		}
		
		user.removeRole(role);
		user.save();

	}

	@Override
	public void setRecoveryPasswordCode(String email,
			String recoveryPasswordCode) {

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return;
		}

		user.recoverPasswordCode = recoveryPasswordCode;
		user.save();

	}

	@Override
	public String getCasinoUserWithConfirmationCode(String confirmationCode) {

		List<User> users = User.find("confirmationCode", confirmationCode)
				.fetch();

		if (users.size() == 0) {

			return null;

		} else if (users.size() > 1) {

			Logger.error("more than one User with  confirmation code "
					+ confirmationCode
					+ " (JPA found). that's an inconsistency - taking the first one...");

		}

		return users.get(0).email;

	}

	@Override
	public void deleteConfirmationCodeOfCasioUser(String email) {

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return;
		}

		user.confirmationCode = "";
		user.save();

	}

	@Override
	public String getUserPasswordHash(String email) {

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return null;
		}

		return user.pwHash;
	}

	@Override
	public void setNewPasswordHashForUser(String email, String passwordHash) {

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return;
		}

		user.pwHash = passwordHash;

		user.save();

	}

	@Override
	public boolean doesUserExist(String email) {

		User user = getOneUserWithEmail(email);

		if (user == null) {
			return false;
		}

		return true;
	}

	@Override
	public String getCasinoUserWithRecoveryPasswordCode(
			String recoverPasswordCode) {

		List<User> users = User
				.find("recoverPasswordCode", recoverPasswordCode).fetch();

		if (users.size() == 0) {

			return null;

		} else if (users.size() > 1) {

			Logger.error("more than one User with  recoverPasswordCode "
					+ recoverPasswordCode
					+ " (JPA found). that's an inconsistency - taking the first one...");

		}

		return users.get(0).email;
	}

	private User getOneUserWithEmail(String email) {

		List<User> users = User.find("email", email).fetch();

		if (users.size() == 0) {

			return null;

		} else if (users.size() > 1) {

			Logger.error("more than one User "
					+ email
					+ " (JPA found). that's an inconsistency - taking the first one...");

		}

		return users.get(0);

	}

}
