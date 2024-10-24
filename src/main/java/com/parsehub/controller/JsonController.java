package com.parsehub.controller;

import com.parsehub.service.JsonService;
import com.parsehub.util.ConversionType;
import com.parsehub.util.Format;
import com.parsehub.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling JSON-related operations.
 * This controller provides endpoints for validating, formatting, and converting JSON data.
 */
@RestController
@RequestMapping("/api/v1/json")
public class JsonController {

    private final JsonService jsonService;

    /**
     * Constructor for JsonController.
     *
     * @param jsonService the JsonService to handle JSON operations
     */
    @Autowired
    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    /**
     * Validates the given JSON.
     *
     * @param json the input JSON string to validate
     * @return ValidationResult object indicating whether the JSON is valid or not
     */
    @PostMapping("/validate")
    public ValidationResult validateJson(@RequestBody String json) {
        return jsonService.validateJson(json);
    }

    /**
     * Formats the given JSON string according to the specified format.
     * The format can be SPACE_2, SPACE_4, or COMPACT.
     *
     * @param json the input JSON string to format
     * @param format the desired format (e.g., SPACE_2, SPACE_4, COMPACT)
     * @return the formatted JSON string
     */
    @PostMapping("/format/{format}")
    public String formatJson(@RequestBody String json, @PathVariable String format) {
        Format formatEnum = Format.valueOf(format.toUpperCase());
        return jsonService.formatJson(json, formatEnum);
    }

    /**
     * Minifies the given JSON string by removing all unnecessary whitespace and formatting.
     *
     * @param json the input JSON string to minify
     * @return the minified JSON string
     */
    @PostMapping("/minify")
    public String minifyJson(@RequestBody String json) {
        return jsonService.minifyJson(json);
    }

    /**
     * Converts the given JSON string into XML format.
     *
     * @param json the input JSON string to convert
     * @return the converted XML string
     */
    @PostMapping("/convert/xml")
    public String convertJsonToXml(@RequestBody String json) {
        return jsonService.convertData(json, ConversionType.XML);
    }

    /**
     * Converts the given JSON string into YAML format.
     *
     * @param json the input JSON string to convert
     * @return the converted YAML string
     */
    @PostMapping("/convert/yaml")
    public String convertJsonToYaml(@RequestBody String json) {
        return jsonService.convertData(json, ConversionType.YAML);
    }

    /**
     * Converts the given JSON string into CSV format.
     *
     * @param json the input JSON string to convert
     * @return the converted CSV string
     */
    @PostMapping("/convert/csv")
    public String convertJsonToCsv(@RequestBody String json) {
        return jsonService.convertData(json, ConversionType.CSV);
    }

    /**
     * Sorts the keys in the JSON alphabetically.
     *
     * @param json the input JSON string to sort
     * @return the sorted JSON string
     */
    @PostMapping("/sort")
    public String sortJson(@RequestBody String json) {
        return jsonService.sortJson(json);
    }
}
