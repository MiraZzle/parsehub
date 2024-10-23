package com.parsehub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.parsehub.util.ValidationResult;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.StringReader;
import com.parsehub.util.ConversionType;

/**
 * Service class for handling XML-related operations such as validation, formatting, and conversion.
 */
@Service
public class XmlService implements IDataService {
    private final XmlMapper xmlMapper;
    private final ObjectMapper objectMapper;
    private final CsvMapper csvMapper;

    /**
     * Constructor that initializes singleton mappers.
     * ObjectMapper, XmlMapper, and CsvMapper are reused across the service methods.
     */
    public XmlService() {
        this.xmlMapper = new XmlMapper(); // Reused for XML-related operations
        this.objectMapper = new ObjectMapper(); // Reused for JSON operations
        this.csvMapper = new CsvMapper(); // Reused for CSV operations
    }

    /**
     * Validates the given XML string by checking if it's well-formed.
     *
     * @param xml the input XML string to validate
     * @return ValidationResult object indicating whether the XML is valid
     */
    public ValidationResult validateXml(String xml) {
        ValidationResult result = new ValidationResult();

        try {
            // Check if the XML is well-formed
            xmlMapper.readTree(new StringReader(xml));
            result.setValid(true);
        } catch (IOException e) {
            result.setValid(false);
            result.addErrorMessage("XML Parsing Error: " + e.getMessage());
        }

        return result;
    }

    /**
     * Converts the given XML string into the specified format (JSON, YAML, or CSV).
     *
     * @param xml the input XML string to convert
     * @param targetType the target conversion format
     * @return the converted data as a string
     */
    public String convertData(String xml, ConversionType targetType) {
        try {
            return switch (targetType) {
                case JSON -> convertXmlToJson(xml);
                case YAML -> convertXmlToYaml(xml);
                case CSV -> convertXmlToCsv(xml);
                default -> xml;
            };
        } catch (Exception e) {
            return "Invalid XML format: " + e.getMessage();
        }
    }

    /**
     * Converts XML to JSON format.
     *
     * @param xml the input XML string
     * @return the converted JSON string
     */
    public String convertXmlToJson(String xml) {
        try {
            JsonNode jsonNode = xmlMapper.readTree(new StringReader(xml));
            return objectMapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }

    /**
     * Converts XML to YAML format by first converting it to JSON and then to YAML.
     *
     * @param xml the input XML string
     * @return the converted YAML string
     */
    public String convertXmlToYaml(String xml) {
        try {
            String json = convertXmlToJson(xml); // First, convert XML to JSON
            JsonNode jsonNode = objectMapper.readTree(json);

            // Convert JSON to YAML
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            Object object = objectMapper.convertValue(jsonNode, Object.class);
            return yaml.dump(object);
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }

    /**
     * Converts XML to CSV format by first converting it to JSON and then to CSV.
     *
     * @param xml the input XML string
     * @return the converted CSV string
     */
    public String convertXmlToCsv(String xml) {
        try {
            JsonNode jsonNode = xmlMapper.readTree(new StringReader(xml));
            CsvSchema.Builder schemaBuilder = CsvSchema.builder();

            if (!jsonNode.isArray()) {
                throw new IllegalArgumentException("XML must represent an array-like structure for CSV conversion");
            }

            // Build schema from the first element of the array
            JsonNode firstElement = jsonNode.elements().next();
            firstElement.fieldNames().forEachRemaining(schemaBuilder::addColumn);

            CsvSchema schema = schemaBuilder.build().withHeader();
            return csvMapper.writer(schema).writeValueAsString(jsonNode);
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }

    /**
     * Minifies the given XML string by removing all indentation and unnecessary whitespace.
     *
     * @param xml the input XML string
     * @return the minified XML string
     */
    public String minifyXml(String xml) {
        try {
            JsonNode tree = xmlMapper.readTree(new StringReader(xml));
            return xmlMapper.writeValueAsString(tree); // Return minified XML
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }
}
