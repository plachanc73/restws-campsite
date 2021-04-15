package ca.qc.plachanc73.restws.web.validation.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * View Model for transferring error message with a list of field errors.
 */
public class FieldErrorVM extends ErrorVM {

	private static final long serialVersionUID = 4798335840059322717L;

	private List<FieldError> fieldErrors;

	public FieldErrorVM(final String error) {
		super(error);
	}

	public void add(String objectName, String field, String message) {
		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
		}
		fieldErrors.add(new FieldErrorVM.FieldError(objectName, field, message));
	}

	private static class FieldError implements Serializable {

		private static final long serialVersionUID = 1L;

		private final String objectName;

		private final String field;

		private final String message;

		public FieldError(String objectName, String field, String message) {
			this.objectName = objectName;
			this.field = field;
			this.message = message;
		}

		@SuppressWarnings("unused")
		public String getObjectName() {
			return objectName;
		}

		@SuppressWarnings("unused")
		public String getField() {
			return field;
		}

		@SuppressWarnings("unused")
		public String getMessage() {
			return message;
		}
	}
}