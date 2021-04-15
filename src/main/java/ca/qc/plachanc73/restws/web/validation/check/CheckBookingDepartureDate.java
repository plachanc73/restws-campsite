package ca.qc.plachanc73.restws.web.validation.check;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.qc.plachanc73.restws.util.DateUtil;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckBookingDepartureDate.CheckBookingDepartureDateValidator.class)
@Documented
public @interface CheckBookingDepartureDate {

	public static final Logger LOGGER = LoggerFactory.getLogger(CheckBookingDepartureDate.class); // NOSONAR

	public static final String CLASS_NAME = "CheckBookingDepartureDate";

	public static final String ARRIVAL_DATE_FIELD = "arrivalDate";

	public static final String DEPARTURE_DATE_FIELD = "departureDate";

	String message() default "The campsite can be reserved for max 3 days so the departure date has to be greater than the arrival date for a maximum of 3 days.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public static class CheckBookingDepartureDateValidator
			implements ConstraintValidator<CheckBookingDepartureDate, Object> {

		@Override
		public void initialize(CheckBookingDepartureDate constraintAnnotation) {
			// Do nothing
		}

		@Override
		public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
			if (object == null)
				return true;

			try {
				Date arrivalDate = (Date) PropertyUtils.getProperty(object, ARRIVAL_DATE_FIELD);
				Date departureDate = (Date) PropertyUtils.getProperty(object, DEPARTURE_DATE_FIELD);
				Date arrivalDatePlusThreeDays = DateUtil.addDays(arrivalDate, 3);

				return !departureDate.before(arrivalDate) && !departureDate.equals(arrivalDate)
						&& !departureDate.after(arrivalDatePlusThreeDays);
			} catch (Exception e) {
				LOGGER.error("This validation has failed : CheckBookingDepartureDate.", e);
				return false;
			}
		}
	}
}