package casino;

import java.util.HashMap;
import java.util.Map;

import models.casino.User;
import play.Play;
import play.mvc.Mailer;
import play.mvc.Router;
import controllers.casino.CasinoConstants;
import controllers.casino.Registration;

public class RegistrationMailer extends Mailer {

	public static void confirmation(User user) {

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("code", user.confirmationCode);
		String confirmation_link = Router.getFullUrl("casino.Registration.confirm",
				args);
		setSubject("Registration Confirmation");

		String recipient = user.email;
		addRecipient(recipient);

		String emailFrom = Play.configuration.getProperty(
				CasinoConstants.emailFrom, "");

		if (emailFrom.equals("")) {

			throw new RuntimeException(
					"casino.emailFrom not set in applications.conf. Doing nothing...");
		} else {

			setFrom(emailFrom);

			send("casino/Registration/confirmation", recipient, confirmation_link);
		}

	}

	public static void lostPassword(User user) {

		user.recoverPasswordCode = Registration.shortUUID();
		user.save();

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("code", user.recoverPasswordCode);
		String confirmation_link = Router.getFullUrl(
				"casino.Registration.lostPasswordNewPassword", args);
		setSubject("Recover Password");

		String recipient = user.email;
		addRecipient(recipient);

		String emailFrom = Play.configuration.getProperty(
				CasinoConstants.emailFrom, "");

		if (emailFrom.equals("")) {

			throw new RuntimeException(
					"casino.emailFrom not set in applications.conf. Doing nothing...");
		} else {

			setFrom(emailFrom);

			send("casino/Registration/lostPasswordEmail", recipient,
					confirmation_link);
		}
	}

}
