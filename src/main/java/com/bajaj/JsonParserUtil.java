package com.bajaj;

import java.io.File;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParserUtil {

    public static String getDestinationValue(String filePath) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(filePath));
        return findFirstDestination(rootNode);
    }

    private static String findFirstDestination(JsonNode node) {
        if (node == null) {
            return "";
        }

        if (node.has("destination")) {
            return node.get("destination").asText();
        }

        if (node.isObject()) {
            for (JsonNode child : node) {
                String value = findFirstDestination(child);
                if (!value.isEmpty()) {
                    return value;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode child : node) {
                String value = findFirstDestination(child);
                if (!value.isEmpty()) {
                    return value;
                }
            }
        }

        return "";
    }
}

