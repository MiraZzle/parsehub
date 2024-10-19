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
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class YamlService {

    private final Yaml yamlParser;

    public YamlService() {
        // Initialize LoaderOptions and Constructor for the YAML parser
        LoaderOptions loaderOptions = new LoaderOptions();
        Constructor constructor = new Constructor(loaderOptions);
        this.yamlParser = new Yaml(constructor);  // Initialize SnakeYAML parser with options
    }

    // Validate YAML
    public ValidationResult validateYaml(String yaml) {
        ValidationResult result = new ValidationResult();
        try {
            // Parse the YAML into a Map to check its validity
            Map<String, Object> yamlData = yamlParser.load(yaml);
            if (yamlData != null) {
                result.setValid(true);
            } else {
                result.setValid(false);
                result.addErrorMessage("YAML is empty or invalid.");
            }
        } catch (ParserException e) {
            // Catch and handle SnakeYAML parsing errors
            result.setValid(false);
            result.addErrorMessage("YAML Parsing Error: " + e.getMessage());
        } catch (Exception e) {
            result.setValid(false);
            result.addErrorMessage("YAML Generic Parsing Error: " + e.getMessage());
        }
        return result;
    }

    // Format YAML (Pretty-print or minified)
    public String formatYaml(String yaml, Format format) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);

            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            // Pretty print or minify based on the format
            StringWriter writer = new StringWriter();
            if (format == Format.COMPACT) {
                // Minified YAML (no pretty printing)
                yamlParser.dump(yamlData, writer);
            } else {
                // Pretty printing: adding spaces for formatting
                Yaml prettyPrinter = new Yaml();  // Use default Yaml object for printing
                prettyPrinter.dump(yamlData, writer);
            }

            return writer.toString();
        } catch (ParserException e) {
            return "YAML Formatting Error: " + e.getMessage();
        } catch (Exception e) {
            return "YAML Formatting Generic Error: " + e.getMessage();
        }
    }

    // Minify YAML (reuse the compact format)
    public String minifyYaml(String yaml) {
        return formatYaml(yaml, Format.COMPACT);
    }


    public String convertData(String yaml, ConversionType targetType) {
        try {
            if (targetType == ConversionType.YAML) {
                return yaml; // If the target is XML, no conversion needed
            }

            switch (targetType) {
                case JSON:
                    return convertYamlToJson(yaml);
                case XML:
                    return convertYamlToXml(yaml);
                case CSV:
                    return convertYamlToCsv(yaml);
                default:
                    return yaml;
            }
        } catch (Exception e) {
            return "Invalid XML format: " + e.getMessage();
        }
    }

    // Convert YAML to JSON (using manual conversion)
    public String convertYamlToJson(String yaml) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);

            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            // Convert the YAML Map to a JSON-like string
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");
            yamlData.forEach((key, value) -> {
                jsonBuilder.append("\"").append(key).append("\": ").append("\"").append(value).append("\", ");
            });
            // Remove the last comma and space
            if (jsonBuilder.length() > 1) {
                jsonBuilder.setLength(jsonBuilder.length() - 2);
            }
            jsonBuilder.append("}");

            return jsonBuilder.toString();
        } catch (ParserException e) {
            return "YAML to JSON Conversion Error: " + e.getMessage();
        } catch (Exception e) {
            return "YAML to JSON Generic Conversion Error: " + e.getMessage();
        }
    }

    // Convert YAML to XML (using manual conversion)
    public String convertYamlToXml(String yaml) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);

            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            // Convert the YAML Map to a simple XML-like string
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<root>");
            yamlData.forEach((key, value) -> {
                xmlBuilder.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
            });
            xmlBuilder.append("</root>");

            return xmlBuilder.toString();
        } catch (ParserException e) {
            return "YAML to XML Conversion Error: " + e.getMessage();
        } catch (Exception e) {
            return "YAML to XML Generic Conversion Error: " + e.getMessage();
        }
    }

    // Convert YAML to CSV (using manual conversion)
    public String convertYamlToCsv(String yaml) {
        try {
            Map<String, Object> yamlData = yamlParser.load(yaml);

            if (yamlData == null) {
                return "Invalid YAML: empty or null";
            }

            // Convert the YAML Map to a simple CSV-like string
            StringBuilder csvBuilder = new StringBuilder();
            yamlData.forEach((key, value) -> {
                csvBuilder.append(key).append(",").append(value).append("\n");
            });

            return csvBuilder.toString();
        } catch (ParserException e) {
            return "YAML to CSV Conversion Error: " + e.getMessage();
        } catch (Exception e) {
            return "YAML to CSV Generic Conversion Error: " + e.getMessage();
        }
    }
}
