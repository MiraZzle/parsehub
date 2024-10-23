package com.parsehub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parsehub.util.ConversionType;
import com.parsehub.util.ValidationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.parsehub.util.Format;
import com.parsehub.util.CustomPrettyPrinter;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Service class for handling JSON-related operations such as validation, formatting, and conversion.
 */
@Service
public class JsonService implements IDataService {
    private final ObjectMapper objectMapper;

    /**
     * Constructor initializes the ObjectMapper for handling JSON operations.
     */
    public JsonService() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Validates the given JSON string.
     *
     * @param json the input JSON string to validate
     * @return ValidationResult object indicating whether the JSON is valid
     */
    public ValidationResult validateJson(String json) {
        ValidationResult result = new ValidationResult();
        try {
            objectMapper.readTree(json);
            result.setValid(true);
        } catch (IOException e) {
            result.setValid(false);
            result.addErrorMessage("Invalid JSON: " + e.getMessage());
        }
        return result;
    }

    /**
     * Formats the given JSON string with the specified indentation format.
     *
     * @param json   the input JSON string to format
     * @param format the desired format (e.g., SPACE_2, SPACE_4)
     * @return the formatted JSON string
     */
    public String formatJson(String json, Format format) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            ObjectWriter writer = switch (format) {
                case SPACE_2 -> objectMapper.writer(new CustomPrettyPrinter(2));
                case SPACE_3 -> objectMapper.writer(new CustomPrettyPrinter(3));
                case SPACE_4 -> objectMapper.writer(new CustomPrettyPrinter(4));
                default -> objectMapper.writer();
            };
            return writer.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }

    /**
     * Minifies the given JSON string by removing all unnecessary spaces and newlines.
     *
     * @param json the input JSON string to minify
     * @return the minified JSON string
     */
    public String minifyJson(String json) {
        return formatJson(json, Format.COMPACT);
    }

    /**
     * Converts the given JSON string into the specified format (XML, YAML, or CSV).
     *
     * @param json the input JSON string to convert
     * @param type the target conversion format
     * @return the converted data as a string
     */
    public String convertData(String json, ConversionType type) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return switch (type) {
                case XML -> convertJsonToXml(jsonNode);
                case YAML -> convertJsonToYaml(jsonNode);
                case CSV -> convertJsonToCsv(jsonNode);
                default -> json;
            };
        } catch (IOException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }


    /**
     * Converts JSON to YAML.
     *
     * @param json the input JSON string
     * @return the converted YAML string
     */
    private String convertJsonToYaml(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            return objectMapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }

    /**
     * Converts JSON to XML.
     *
     * @param jsonNode the parsed JSON data
     * @return the converted XML string
     * @throws JsonProcessingException if the conversion fails
     */
    private String convertJsonToXml(JsonNode jsonNode) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(jsonNode);
    }


    private String convertJsonToYaml(JsonNode jsonNode) {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        Object object = objectMapper.convertValue(jsonNode, Object.class);
        return yaml.dump(object);
    }

    /**
     * Converts JSON to CSV.
     *
     * @param jsonNode the parsed JSON data
     * @return the converted CSV string
     * @throws JsonProcessingException if the conversion fails
     */
    private String convertJsonToCsv(JsonNode jsonNode) throws JsonProcessingException {
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

    /**
     * Sorts the JSON keys alphabetically.
     *
     * @param json the input JSON string to sort
     * @return the sorted JSON string
     */
    public String sortJson(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            TreeMap<String, Object> sortedMap = new TreeMap<>(objectMapper.convertValue(jsonNode, TreeMap.class));
            return objectMapper.writeValueAsString(sortedMap);
        } catch (IOException e) {
            return "Invalid JSON format: " + e.getMessage();
        }
    }
}
