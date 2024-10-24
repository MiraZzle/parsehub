package com.parsehub.controller;

import com.parsehub.service.YamlService;
import com.parsehub.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling YAML-related API endpoints.
 * This controller provides operations for validating, minifying, and converting YAML data into other formats like JSON, XML, and CSV.
 */
@RestController
@RequestMapping("/api/v1/yaml")
public class YamlController {

    private final YamlService yamlService;

    /**
     * Constructor for YamlController.
     *
     * @param yamlService the YamlService to handle YAML operations
     */
    @Autowired
    public YamlController(YamlService yamlService) {
        this.yamlService = yamlService;
    }

    /**
     * Validates the provided YAML string.
     *
     * @param yaml the YAML string to validate
     * @return ValidationResult indicating if the YAML is valid or not
     */
    @PostMapping("/validate")
    public ValidationResult validateYaml(@RequestBody String yaml) {
        return yamlService.validateYaml(yaml);
    }

    /**
     * Minifies the provided YAML string by removing unnecessary whitespace and formatting.
     *
     * @param yaml the YAML string to minify
     * @return the minified YAML string
     */
    @PostMapping("/minify")
    public String minifyYaml(@RequestBody String yaml) {
        return yamlService.minifyYaml(yaml);
    }

    /**
     * Converts the provided YAML string to JSON format.
     *
     * @param yaml the YAML string to convert
     * @return the converted JSON string
     */
    @PostMapping("/convert/json")
    public String convertYamlToJson(@RequestBody String yaml) {
        return yamlService.convertYamlToJson(yaml);
    }

    /**
     * Converts the provided YAML string to XML format.
     *
     * @param yaml the YAML string to convert
     * @return the converted XML string
     */
    @PostMapping("/convert/xml")
    public String convertYamlToXml(@RequestBody String yaml) {
        return yamlService.convertYamlToXml(yaml);
    }

    /**
     * Converts the provided YAML string to CSV format.
     *
     * @param yaml the YAML string to convert
     * @return the converted CSV string
     */
    @PostMapping("/convert/csv")
    public String convertYamlToCsv(@RequestBody String yaml) {
        return yamlService.convertYamlToCsv(yaml);
    }
}
