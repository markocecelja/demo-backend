package com.example.common.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationError {

	UNRECOGNIZED_EXCEPTION("Unrecognized exception!"),
	JSON_PARSE_ERROR("Error occurred while parsing JSON!"),
	BAD_REQUEST("Bad request!"),
	NON_EXISTING_CUSTOMER_STATUS("Customer status doesn't exist!"),
	NON_EXISTING_CUSTOMER("Customer doesn't exist!");

	private final String description;
}
