package ca.qc.plachanc73.restws.web.validation.error;

import java.io.Serializable;

/**
 * The root view model for all error responses from rest API.
 */
public class ErrorVM implements Serializable {

	private static final long serialVersionUID = -249387842251845251L;

	protected final String error;

	public ErrorVM(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}