package ca.qc.plachanc73.restws.exception;

public class PreconditionFailedServiceException extends ServiceException {

	private static final long serialVersionUID = -4437410131548521264L;

	public PreconditionFailedServiceException(String message) {
		super(message);
	}
}