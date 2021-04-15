package ca.qc.plachanc73.restws.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -7079986859239175774L;

	public ServiceException(String message) {
		super(message);
	}
}