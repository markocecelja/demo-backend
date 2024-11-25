package com.example.common.utils;

import com.example.common.exceptions.ApplicationError;
import com.example.common.exceptions.ApplicationException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JsonHelper {

	public static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.registerModule(new JavaTimeModule());
	}

	public static String serialise(Object obj) throws ApplicationException {
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (IOException e) {
			throw new ApplicationException(ApplicationError.JSON_PARSE_ERROR, e);
		}
	}

	public static String combineAndSerialise(Object mainObject, String var, Object branchObject) throws ApplicationException {
		JsonNode main = turnIntoJsonTree(mainObject);
		JsonNode branch = turnIntoJsonTree(branchObject);
		JsonTreeHelper.addElementToJsonTree(main, var, branch);
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(main);
		} catch (IOException e) {
			throw new ApplicationException(ApplicationError.JSON_PARSE_ERROR, e);
		}
	}

	public static JsonNode turnIntoJsonTree(Object obj) {
		return objectMapper.valueToTree(obj);
	}

	public static <T> T deserialise(JsonNode node, Class<T> objectClass) throws ApplicationException {
		if (node == null) {
			return null;
		}

		try {
			return objectMapper.treeToValue(node, objectClass);
		} catch (IOException e) {
			throw new ApplicationException(ApplicationError.JSON_PARSE_ERROR, e);
		}
	}
}
