package com.example.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonTreeHelper {

	public static void addElementToJsonTree(JsonNode mainTree, String nodeName, JsonNode branch) {
		if (mainTree.has(nodeName)) {
			((ObjectNode) mainTree).remove(nodeName);
		}

		((ObjectNode) mainTree).set(nodeName, branch);
	}
}
