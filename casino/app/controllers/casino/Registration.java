package controllers.casino;

import play.Play;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.IsTrue;
import play.data.validation.MinSize;
import play.data.validation.Required;
import ugot.recaptcha.Recaptcha;
import casino.Casino;
import casino.RegistrationMailer;

public class Registration extends TransportUriGuarantee {

	// ////////////////////////////////////////////////////////////////////////
	// Sign up (register) new user:
	// ////////////////////////////////////////////////////////////////////////
	/**
	 * Show registration screen.
	 */
	public static void registration() {

		render();
	}

	/**
	 * Do the validation of the registration screen
	 */
	public static void registrationFinish(@Recaptcha String captcha,
			@Required @Email String email,
			@Required @Equals("confirm") @MinSize(8) String password,
			@Required @MinSize(8) String confirm,
			@IsTrue Boolean acceptTermsOfService) {

		// check that form is really from user:
		checkAuthenticity();

		boolean hasErrors = true;

		if (Play.id.equals("test")) {

			// we are in test mode.. ignore wrong captcha
			if (validation.errors().size() == 1) {

				if (validation.errors().get(0).getKey().equals("captcha")) {
					hasErrors = false;
				}
			}

		} else {

			hasErrors = validation.hasErrors();

		}

		// check if user exists and don't allow to register user:
		if (!hasErrors) {

			if (Casino.getCasinoUser().doesUserExist(email)) {
				hasErrors = true;

			}
		}

		// save or display error
		if (hasErrors) {

			flash.error("registration.error");
			validation.keep();
			params.flash("email");

			// If acceptTermsOfService is not checked it is not sent to server..
			// therefore we check for null...
			if (acceptTermsOfService != null) {
				params.flash("acceptTermsOfService");
			}

			registration();

		} else {

			String passwordHash = Casino.getHashForPassword(password);
			String confirmationCode = Casino.shortUUID();

			Casino.getCasinoUser().createNewCasinoUser(email, passwordHash,
					confirmationCode);

			RegistrationMailer.confirmation(email, confirmationCode);
			pending();

		}

	}

	/**
	 * registration was successful => show message to user that an email was
	 * sent for final activation of user account.
	 */
	public static void pending() {

		render();
	}

	/**
	 * Checks and activates user account with code sent in email.
	 */
	public static void confirm(String code) {

		if (code.length() == 0) {
			flash.error("registration.error");

		}

		// get user with this email

		String email = Casino.getCasinoUser()
				.getCasinoUserWithConfirmationCode(code);

		if (email != null) {

			Casino.getCasinoUser().deleteConfirmationCodeOfCasioUser(email);

			// we also log in user after successful confirmation of email...
			session.put("username", email);

			flash.success("registration.registration_success");
		} else {

			flash.error("registration.error");
		}

		render();
	}

	// ////////////////////////////////////////////////////////////////////////
	// Recover lost password of user using email:
	// ////////////////////////////////////////////////////////////////////////

	/**
	 * Show input window for email.
	 */
	public static void lostPassword() {

		render();

	}

	/**
	 * Validate the input of email... check recaptcha and so on...
	 */
	public static void lostPasswordFinish(@Required @Email String email,
			@Recaptcha String captcha) {

		// /check:
		checkAuthenticity();

		boolean hasErrors = true;

		if (Play.id.equals("test")) {

			// we are in test mode.. ignore wrong captcha
			if (validation.errors().size() == 1) {

				if (validation.errors().get(0).getKey().equals("captcha")) {
					hasErrors = false;
				}
			}

		} else {

			hasErrors = validation.hasErrors();

		}

		// save or display error
		if (hasErrors) {

			flash.error("registration.error");
			params.flash("email");
			validation.keep();
			lostPassword();

		} else {

			// check if user exists and send email
			// always display success message...

			if (Casino.getCasinoUser().doesUserExist(email)) {
				// user found.. displaying notification.
				// send notification

				String recoverPasswordCode = Casino.shortUUID();
				
				Casino.getCasinoUser().setRecoveryPasswordCode(email, recoverPasswordCode);

				RegistrationMailer.lostPassword(email, recoverPasswordCode);

				// display notification to user
				lostPasswordEmailSentCheckInbox();

			} else {
				// System.out.println("user not found.. displaying notification anyway.");
				// display success anyway don't allow robots to check which
				// emails are signed up.
				lostPasswordEmailSentCheckInbox();

			}

		}

	}

	/**
	 * email lookup was successful => tell user to check inbox to re-enable
	 * password.
	 */
	public static void lostPasswordEmailSentCheckInbox() {

		render();

	}

	/**
	 * User has valid confirmation => we allow to generate new password
	 */
	public static void lostPasswordNewPassword(@Required String code) {

		render(code);

	}

	/**
	 * Validation for the lostPasswordNewPassword screen
	 */
	public static void lostPasswordNewPasswordFinish(@Required String code,
			@Recaptcha String captcha,
			@Required @Equals("passwordConfirm") @MinSize(8) String password,
			@Required @MinSize(8) String passwordConfirm) {

		// check:
		checkAuthenticity();

		boolean hasErrors = true;

		if (Play.id.equals("test")) {

			// we are in test mode.. ignore wrong captcha
			if (validation.errors().size() == 1) {

				if (validation.errors().get(0).getKey().equals("captcha")) {
					hasErrors = false;
				}
			}

		} else {

			hasErrors = validation.hasErrors();

		}

		// save or display error
		if (hasErrors) {

			flash.error("registration.error");
			params.flash("password");
			params.flash("passwordConfirm");
			validation.keep();
			lostPasswordNewPassword(code);

		} else {

			String email = Casino.getCasinoUser()
					.getCasinoUserWithRecoveryPasswordCode(code);

			if (email == null) {
				// hmm. does not exist. display success anyway...
				// we don't want to let robots sniff...

				render();
				

			} else {

				Casino.getCasinoUser().setRecoveryPasswordCode(email, code);

				// compute hash...
				String passwordHash = Casino.getHashForPassword(password);

				Casino.getCasinoUser().setNewPasswordHashForUser(email,
						passwordHash);

				render();

			}

		}

	}

}
