package casino;

import java.util.HashMap;
import java.util.Map;

import play.Play;
import play.mvc.Mailer;
import play.mvc.Router;

public class RegistrationMailer extends Mailer {

	public static void confirmation(String email, String confirmationCode) {

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("code", confirmationCode);
		String confirmation_link = Router.getFullUrl("casino.Registration.confirm",
				args);
		setSubject("Registration Confirmation");


		addRecipient(email);

		String emailFrom = Play.configuration.getProperty(
				CasinoApplicationConfConstants.EMAIL_FROM, "");

		if (emailFrom.equals("")) {

			throw new RuntimeException(
					"casino.emailFrom not set in applications.conf. Doing nothing...");
		} else {

			setFrom(emailFrom);

			send("casino/Registration/confirmation", email, confirmation_link);
		}

	}

	public static void lostPassword(String email, String recoverPasswordCode) {

		//user.recoverPasswordCode = Registration.shortUUID();
		//user.save();

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("code", recoverPasswordCode);
		String confirmation_link = Router.getFullUrl(
				"casino.Registration.lostPasswordNewPassword", args);
		setSubject("Recover Password");

		
		addRecipient(email);

		String emailFrom = Play.configuration.getProperty(
				CasinoApplicationConfConstants.EMAIL_FROM, "");

		if (emailFrom.equals("")) {

			throw new RuntimeException(
					"casino.emailFrom not set in applications.conf. Doing nothing...");
		} else {

			setFrom(emailFrom);

			send("casino/Registration/lostPasswordEmail", email,
					confirmation_link);
		}
	}

}
