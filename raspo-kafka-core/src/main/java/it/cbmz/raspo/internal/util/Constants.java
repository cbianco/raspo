package it.cbmz.raspo.internal.util;

public interface Constants {

	interface Command {

		String SPEED_TEST = "speedTestClient";

		String PING = "pingClient";

	}

	interface Handler {
		String DOWNLOAD_HANDLER = "raspo/downloadSpeed";
		String UPLOAD_HANDLER = "raspo/uploadSpeed";
		String SEND_HANDLER = "raspo/sendMessage";
		String ERROR_HANDLER = "raspo/error";
		String START_SPEED_TEST_HANDLER = "raspo/start";

		interface EventKey {
			String MESSAGE_KEY = "message";
			String EXCEPTION_KEY = "exception";
		}

	}

	interface Message {

		interface Key {

		}

	}

}
