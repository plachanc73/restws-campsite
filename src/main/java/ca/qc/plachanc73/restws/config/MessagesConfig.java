package ca.qc.plachanc73.restws.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessagesConfig {

	private static ResourceBundle resourceBundleMessagesEnglish = ResourceBundle.getBundle("messages", Locale.ENGLISH,
			new UTF8Control());

	private MessagesConfig() {
		// No initialization
	}

	public static String getMessage(String messageKey) {
		return resourceBundleMessagesEnglish.getString(messageKey);
	}
}