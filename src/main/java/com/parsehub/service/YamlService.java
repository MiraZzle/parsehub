package com.parsehub.service;

import com.parsehub.util.ConversionType;
import com.parsehub.util.ValidationResult;
import com.parsehub.util.Format;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.StringWriter;
import java.util.Map;

/**
 * Service class for handling YAML-related operations such as validation, formatting, and conversion.
 */
@Service
public class YamlService implements IDataService {
    private final Yaml yamlParser;

    /**
     * Initializes the service with a singleton instance of SnakeYAML parser.
     * The parser is initialized with custom LoaderOptions and Constructor for performance and flexibility.
     */
    public YamlService() {
        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(loaderOptions);
        this.yamlParser = new Yaml(constructor);
    }

    /**
     * Validates a YAML string by parsing it into a map.
     *
     * @param yaml the YAML string to validate
     * @return ValidationResult indicating whether the YAML is valid
     */
    public ValidationResult validateYaml(String yaml) {
        ValidationResult result = new ValidationResult();
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);
            if (yamlData != null) {
                result.setValid(true);
            } else {
                result.setValid(false);
                result.addErrorMessage("YAML is empty or invalid.");
            }
        } catch (ParserException e) {
            result.setValid(false);
            result.addErrorMessage("YAML Parsing Error: " + e.getMessage());
        } catch (Exception e) {
            result.setValid(false);
            result.addErrorMessage("YAML Generic Parsing Error: " + e.getMessage());
        }
        return result;
    }

    /**
     * Formats a YAML string either as pretty-printed or minified.
     *
     * @param yaml   the YAML string to format
     * @param format the format type (pretty or compact)
     * @return the formatted YAML string
     */
    public String formatYaml(String yaml, Format format) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);
            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            StringWriter writer = new StringWriter();
            if (format == Format.COMPACT) {
                yamlParser.dump(yamlData, writer); // Minified YAML
            } else {
                Yaml prettyPrinter = new Yaml();  // Use default Yaml for pretty-printing
                prettyPrinter.dump(yamlData, writer);
            }
            return writer.toString();
        } catch (ParserException e) {
            return "YAML Formatting Error: " + e.getMessage();
        } catch (Exception e) {
            return "YAML Formatting Generic Error: " + e.getMessage();
        }
    }

    /**
     * Converts YAML to different formats (JSON, XML, or CSV).
     *
     * @param yaml       the input YAML string
     * @param targetType the target format type
     * @return the converted string
     */
    public String convertData(String yaml, ConversionType targetType) {
        try {
            return switch (targetType) {
                case JSON -> convertYamlToJson(yaml);
                case XML -> convertYamlToXml(yaml);
                case CSV -> convertYamlToCsv(yaml);
                default -> yaml;
            };
        } catch (Exception e) {
            return "Invalid YAML format: " + e.getMessage();
        }
    }

    /**
     * Converts YAML to JSON format.
     *
     * @param yaml the YAML string
     * @return the converted JSON string
     */
    public String convertYamlToJson(String yaml) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);
            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");
            yamlData.forEach((key, value) -> jsonBuilder.append("\"").append(key).append("\": \"").append(value).append("\", "));
            if (jsonBuilder.length() > 1) {
                jsonBuilder.setLength(jsonBuilder.length() - 2); // Remove the last comma
            }
            jsonBuilder.append("}");
            return jsonBuilder.toString();
        } catch (ParserException e) {
            return "YAML to JSON Conversion Error: " + e.getMessage();
        }
    }

    /**
     * Converts YAML to XML format.
     *
     * @param yaml the YAML string
     * @return the converted XML string
     */
    public String convertYamlToXml(String yaml) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);
            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<root>");
            yamlData.forEach((key, value) -> xmlBuilder.append("<").append(key).append(">").append(value).append("</").append(key).append(">"));
            xmlBuilder.append("</root>");
            return xmlBuilder.toString();
        } catch (ParserException e) {
            return "YAML to XML Conversion Error: " + e.getMessage();
        }
    }

    /**
     * Converts YAML to CSV format.
     *
     * @param yaml the YAML string
     * @return the converted CSV string
     */
    public String convertYamlToCsv(String yaml) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);
            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            StringBuilder csvBuilder = new StringBuilder();
            yamlData.forEach((key, value) -> csvBuilder.append(key).append(",").append(value).append("\n"));
            return csvBuilder.toString();
        } catch (ParserException e) {
            return "YAML to CSV Conversion Error: " + e.getMessage();
        }
    }

    /**
     * Minifies the YAML by removing spaces (compact format).
     *
     * @param yaml the YAML string
     * @return the minified YAML string
     */
    public String minifyYaml(String yaml) {
        return formatYaml(yaml, Format.COMPACT);
    }
}
