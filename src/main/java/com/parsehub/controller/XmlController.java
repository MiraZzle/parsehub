package com.parsehub.controller;

import com.parsehub.service.XmlService;
import com.parsehub.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling XML-related API endpoints.
 * This controller provides operations for validating, minifying, and converting XML data into other formats like JSON, YAML, and CSV.
 */
@RestController
@RequestMapping("/api/v1/xml")
public class XmlController {

    private final XmlService xmlService;

    /**
     * Constructor for XmlController.
     *
     * @param xmlService the XmlService to handle XML operations
     */
    @Autowired
    public XmlController(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    /**
     * Validates the provided XML string.
     *
     * @param xml the XML string to validate
     * @return ValidationResult indicating if the XML is valid or not
     */
    @PostMapping("/validate")
    public ValidationResult validateXml(@RequestBody String xml) {
        return xmlService.validateXml(xml);
    }

    /**
     * Minifies the provided XML string by removing unnecessary whitespace and formatting.
     *
     * @param xml the XML string to minify
     * @return the minified XML string
     */
    @PostMapping("/minify")
    public String minifyXml(@RequestBody String xml) {
        return xmlService.minifyXml(xml);
    }

    /**
     * Converts the provided XML string to JSON format.
     *
     * @param xml the XML string to convert
     * @return the converted JSON string
     */
    @PostMapping("/convert/json")
    public String convertXmlToJson(@RequestBody String xml) {
        return xmlService.convertXmlToJson(xml);
    }

    /**
     * Converts the provided XML string to YAML format.
     *
     * @param xml the XML string to convert
     * @return the converted YAML string
     */
    @PostMapping("/convert/yaml")
    public String convertXmlToYaml(@RequestBody String xml) {
        return xmlService.convertXmlToYaml(xml);
    }

    /**
     * Converts the provided XML string to CSV format.
     *
     * @param xml the XML string to convert
     * @return the converted CSV string
     */
    @PostMapping("/convert/csv")
    public String convertXmlToCsv(@RequestBody String xml) {
        return xmlService.convertXmlToCsv(xml);
    }
}
