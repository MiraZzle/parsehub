package com.parsehub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parsehub.util.ConversionType;
import com.parsehub.util.ValidationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.parsehub.util.Format;
import com.parsehub.util.CustomPrettyPrinter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.TreeMap;

@Service
public class JsonService {
    private final ObjectMapper objectMapper;

    public JsonService() {
        this.objectMapper = new ObjectMapper();
    }

    // Method to validate JSON
    public ValidationResult validateJson(String json) {
        ValidationResult result = new ValidationResult();
        try {
            // Try parsing the JSON
            objectMapper.readTree(json);
            result.setValid(true); // If no exception, it's valid
        } catch (IOException e) {
            result.setValid(false);
            result.addErrorMessage("Invalid JSON: " + e.getMessage());
        }
        return result;
    }

    // Method to format JSON
    // Method to format JSON with different styles
    public String formatJson(String json, Format format) {
        try {
            // Parse the JSON into a JsonNode
            JsonNode jsonNode = objectMapper.readTree(json);

            // Choose the correct formatting style
            ObjectWriter writer;
            switch (format) {
                case SPACE_2:
                    writer = objectMapper.writer(new CustomPrettyPrinter(2));
                    break;
                case SPACE_3:
                    writer = objectMapper.writer(new CustomPrettyPrinter(3));
                    break;
                case SPACE_4:
                    writer = objectMapper.writer(new CustomPrettyPrinter(4));
                    break;
                case COMPACT:
                default:
                    writer = objectMapper.writer();
            }
            // Return formatted JSON
            return writer.writeValueAsString(jsonNode);

        } catch (JsonProcessingException e) {
            // Handle exception
            return "Invalid JSON format: " + e.getMessage();
        }
    }


    // Method to minify JSON
    public String minifyJson(String json) {
        return formatJson(json, Format.COMPACT); // Reuse formatJson with a compact setting
    }

    // Method to convert JSON to other formats (example: to YAML)
    public String convertJson(String json, ConversionType type) {
        return "";
    }

    // Method to sort JSON keys alphabetically
    public String sortJson(String json) {
        try {
            // Parse the JSON into a TreeMap to automatically sort the keys
            JsonNode jsonNode = objectMapper.readTree(json);
            TreeMap<String, Object> sortedMap = new TreeMap<>(objectMapper.convertValue(jsonNode, TreeMap.class));

            // Convert sorted TreeMap back to JSON
            return objectMapper.writeValueAsString(sortedMap);
        } catch (IOException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }
}
