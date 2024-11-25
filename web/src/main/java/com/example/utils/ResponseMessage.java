package com.example.utils;

import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import com.example.common.utils.JsonHelper;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage<T> {

	private String status;

	private String errorCode;

	private String errorMessage;

	private T payload;

	public ResponseMessage(String status, ApplicationError error) {
		this(status, (error != null ? error.name() : null), (error != null ? error.getDescription() : null));
	}

	public ResponseMessage(String status, ApplicationError error, String errorMessage) {
		this(status, (error != null ? error.name() : null), errorMessage);
	}

	public ResponseMessage(String status, String errorCode, String message) {
		this(status, errorCode, message, null);
	}

	public ResponseMessage(String status, String errorCode, String message, T payload) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = message;
		this.payload = payload;
	}

	public ResponseMessage(T payload) {
		this("OK", null, null, payload);
	}

	public static <T> String getMessageAsJson(ResponseMessage<T> message) throws ApplicationException {
		if (message.payload != null) {
			Object branch = message.payload;
			message.payload = null;
			return JsonHelper.combineAndSerialise(message, "payload", branch);
		} else
			return JsonHelper.serialise(message);
	}

	public static <T> String packageAndJsoniseError(ApplicationError applicationError) throws ApplicationException {
		ResponseMessage<T> replyObject = new ResponseMessage<>("ERROR", applicationError);
		return ResponseMessage.getMessageAsJson(replyObject);
	}

	public static <T> String packageAndJsoniseError(ApplicationError error, String message) throws ApplicationException {
		ResponseMessage<T> replyObject = new ResponseMessage<>("ERROR", error, message);
		return ResponseMessage.getMessageAsJson(replyObject);
	}
}
