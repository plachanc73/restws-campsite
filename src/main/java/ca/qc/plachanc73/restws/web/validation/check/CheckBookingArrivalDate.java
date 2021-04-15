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
@Constraint(validatedBy = CheckBookingArrivalDate.CheckBookingArrivalDateValidator.class)
@Documented
public @interface CheckBookingArrivalDate {

	public static final Logger LOGGER = LoggerFactory.getLogger(CheckBookingArrivalDate.class); // NOSONAR

	public static final String CLASS_NAME = "CheckBookingArrivalDate";

	public static final String ARRIVAL_DATE_FIELD = "arrivalDate";

	String message() default "The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public static class CheckBookingArrivalDateValidator
			implements ConstraintValidator<CheckBookingArrivalDate, Object> {

		@Override
		public void initialize(CheckBookingArrivalDate constraintAnnotation) {
			// Do nothing
		}

		@Override
		public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
			if (object == null)
				return true;

			try {
				Date arrivalDate = (Date) PropertyUtils.getProperty(object, ARRIVAL_DATE_FIELD);
				DateUtil dateUtil = new DateUtil();
				Date today = dateUtil.getToday();
				Date tomorrow = dateUtil.getTomorrow();
				Date dateToMax = DateUtil.addMonths(today, 1);

				return !arrivalDate.before(tomorrow) && !arrivalDate.after(dateToMax);
			} catch (Exception e) {
				LOGGER.error("This validation has failed : CheckBookingArrivalDate.", e);
				return false;
			}
		}
	}
}