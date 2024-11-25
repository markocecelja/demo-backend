package com.example.utils;

import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

	@ExceptionHandler({ApplicationException.class})
	public ResponseEntity<Object> handleApplicationException(ApplicationException applicationException) throws ApplicationException {

		String response;

		if (applicationException.getErrorData() != null) {
			response = ResponseMessage.packageAndJsoniseError(applicationException.getError(), applicationException.getErrorData().toString());
		} else {
			response = ResponseMessage.packageAndJsoniseError(applicationException.getError());
		}


		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler({
			NumberFormatException.class,
			MethodArgumentNotValidException.class,
			ConstraintViolationException.class,
			MissingServletRequestParameterException.class,
			HttpMessageNotReadableException.class,
			BindException.class,
			DateTimeParseException.class
	})
	public ResponseEntity<Object> handleBadRequestExceptions(Exception exception) throws ApplicationException {

		log.error("Bad request exception occurred!", exception);

		String response = ResponseMessage.packageAndJsoniseError(ApplicationError.BAD_REQUEST, exception.toString());

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(Exception exception) throws ApplicationException {

		log.error("Unrecognized exception occurred!", exception);

		String response = ResponseMessage.packageAndJsoniseError(ApplicationError.UNRECOGNIZED_EXCEPTION);


		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
