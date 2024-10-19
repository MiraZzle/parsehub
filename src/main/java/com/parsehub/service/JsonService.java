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
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
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
            return writer.writeValueAsString(jsonNode);

        } catch (JsonProcessingException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }

    public String minifyJson(String json) {
        return formatJson(json, Format.COMPACT); // Reuse formatJson with a compact setting
    }

    public String convertData(String json, ConversionType type) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);  // Parse JSON to JsonNode
            switch (type) {
                case XML:
                    return convertJsonToXml(jsonNode);
                case YAML:
                    return convertJsonToYaml(jsonNode);
                case CSV:
                    return convertJsonToCsv(jsonNode);
                default:
                    return "Unsupported conversion type";
            }
        } catch (IOException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }

    private String convertJsonToYaml(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            return objectMapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }

    private String convertJsonToXml(JsonNode jsonNode) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(jsonNode);
    }

    private String convertJsonToYaml(JsonNode jsonNode) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);  // Multi-line format

        Yaml yaml = new Yaml(options);
        Object object = objectMapper.convertValue(jsonNode, Object.class);  // Convert JsonNode to Java Object
        return yaml.dump(object);  // Convert Java Object to YAML string
    }

    private String convertJsonToCsv(JsonNode jsonNode) throws JsonProcessingException {
        // Ensure the JSON is an array of objects - needed to convert to CSV
        if (!jsonNode.isArray()) {
            throw new IllegalArgumentException("JSON must be an array to convert to CSV");
        }

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema.Builder schemaBuilder = CsvSchema.builder();

        // Build schema based on the first element of the array (assumed to be an object)
        JsonNode firstElement = jsonNode.elements().next();
        firstElement.fieldNames().forEachRemaining(fieldName -> schemaBuilder.addColumn(fieldName));

        CsvSchema schema = schemaBuilder.build().withHeader();
        return csvMapper.writer(schema).writeValueAsString(jsonNode);
    }

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
