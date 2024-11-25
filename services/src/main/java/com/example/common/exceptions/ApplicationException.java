package com.example.common.exceptions;

import jakarta.servlet.ServletException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends ServletException {

	private Object errorData;

	private ApplicationError error;

	public ApplicationException(ApplicationError error) {
		this(error, error.getDescription(), null);
	}

	public ApplicationException(ApplicationError error, String errorMessage) {
		this(error, errorMessage, null);
	}

	public ApplicationException(ApplicationError error, Throwable throwable) {
		this(error, null, throwable);
	}

	public ApplicationException(ApplicationError error, String errorMessage, Throwable t) {
		super(t);
		this.error = error;
		this.errorData = errorMessage;
	}
}
