package ca.qc.plachanc73.restws.web.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractRestController implements Controller {

	protected static final String TEXT_NOT_FOUND = " is not found.";

	@Autowired
	protected HttpServletRequest httpRequest;

	@Override
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(HttpMessageNotReadableException e) {
		// Returns HTTP 400 Bad Request
		throw e;
	}
}