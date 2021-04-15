package ca.qc.plachanc73.restws.web.validation;

import static ca.qc.plachanc73.restws.util.ExceptionUtil.getStringFromStackTrace;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ca.qc.plachanc73.restws.exception.BadRequestServiceException;
import ca.qc.plachanc73.restws.exception.ConflictServiceException;
import ca.qc.plachanc73.restws.exception.PreconditionFailedServiceException;
import ca.qc.plachanc73.restws.exception.ServiceException;
import ca.qc.plachanc73.restws.web.validation.error.ErrorVM;
import ca.qc.plachanc73.restws.web.validation.error.FieldErrorVM;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class);

	/**
	 * Default Handler for Backend exceptions.
	 * 
	 * @param exception
	 * @return {@link ErrorVM}
	 **/
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorVM process500Exception(Exception exception) {
		LOGGER.error(getStringFromStackTrace(exception));
		return new ErrorVM(exception.getMessage());
	}

	/**
	 * Handler for Data Integrity Violation exceptions.
	 * 
	 * @param dataIntegrityViolationException
	 * @return {@link ErrorVM}
	 **/
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorVM process500Exception(DataIntegrityViolationException dataIntegrityViolationException) {
		Throwable cause;
		for (cause = dataIntegrityViolationException.getCause(); cause != null; cause = cause.getCause()) {
			if (cause instanceof SQLException) {
				break;
			}
		}

		LOGGER.error(getStringFromStackTrace(cause));

		return new ErrorVM(cause == null ? dataIntegrityViolationException.getMessage() : cause.getMessage());
	}

	/**
	 * Handler for Service exceptions.
	 * 
	 * @param serviceException
	 * @return {@link ErrorVM}
	 **/
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorVM process500Exception(ServiceException serviceException) {
		LOGGER.error(getStringFromStackTrace(serviceException));
		return new ErrorVM(serviceException.getMessage());
	}

	/**
	 * Handler for ConflictService exceptions.
	 * 
	 * @param conflictServiceException
	 * @return {@link ErrorVM}
	 **/
	@ExceptionHandler(ConflictServiceException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorVM process409Exception(ConflictServiceException conflictServiceException) {
		LOGGER.info(conflictServiceException.getMessage());
		return new ErrorVM(conflictServiceException.getMessage());
	}

	/**
	 * Handler for PreconditionFailedService exceptions.
	 * 
	 * @param preconditionFailedServiceException
	 * @return {@link ErrorVM}
	 **/
	@ExceptionHandler(PreconditionFailedServiceException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	@ResponseBody
	public ErrorVM process409Exception(PreconditionFailedServiceException preconditionFailedServiceException) {
		LOGGER.info(preconditionFailedServiceException.getMessage());
		return new ErrorVM(preconditionFailedServiceException.getMessage());
	}

	/**
	 * Handler for BadRequestService exceptions.
	 * 
	 * @param badRequestServiceException
	 * @return {@link ErrorVM}
	 **/
	@ExceptionHandler(BadRequestServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorVM process400Exception(BadRequestServiceException badRequestServiceException) {
		LOGGER.info(badRequestServiceException.getMessage());
		return new ErrorVM(badRequestServiceException.getMessage());
	}

	/**
	 * Handler for MethodArgumentNotValid exceptions.
	 * 
	 * @param methodArgumentNotValidException
	 * @return {@link FieldErrorVM}
	 **/
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public FieldErrorVM process400Exception(MethodArgumentNotValidException methodArgumentNotValidException) {
		LOGGER.info(methodArgumentNotValidException.getMessage());
		BindingResult result = methodArgumentNotValidException.getBindingResult();
		FieldErrorVM dto = new FieldErrorVM(methodArgumentNotValidException.getMessage());
		for (FieldError fieldError : result.getFieldErrors()) {
			dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
		}
		for (ObjectError globalError : result.getGlobalErrors()) {
			dto.add(globalError.getObjectName(), globalError.getObjectName(), globalError.getCode());
		}
		return dto;
	}
}