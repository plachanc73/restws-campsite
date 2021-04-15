package ca.qc.plachanc73.restws.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	private ExceptionUtil() {
		// No initialization
	}

	/**
	 * Method to get the stacktrace of an exception as a string. Useful to log the
	 * stacktrace.
	 *
	 * @param cause
	 * @return
	 */
	public static String getStringFromStackTrace(Throwable cause) {
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		cause.printStackTrace(printWriter);
		printWriter.flush();

		return writer.toString();
	}
}