package com.parsehub.controller;

import com.parsehub.service.JsonService;
import com.parsehub.util.ConversionType;
import com.parsehub.util.Format;
import com.parsehub.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  // Use @RestController instead of @Controller to return response bodies directly
@RequestMapping("/api/v1/json")
public class JsonController {

    private final JsonService jsonService;

    @Autowired
    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @PostMapping("/validate")
    public ValidationResult validateJson(@RequestBody String json) {
        // log the request
        System.out.println("Request received: " + json);

        return jsonService.validateJson(json);  // Delegate to the service
    }

    @PostMapping("/format/{format}")
    public String formatJson(@RequestBody String json, @PathVariable String format) {
        // Map the format String to the Format enum
        Format formatEnum = Format.valueOf(format.toUpperCase());  // Convert the string to an enum
        return jsonService.formatJson(json, formatEnum);  // Delegate to the service
    }

    @PostMapping("/minify")
    public String minifyJson(@RequestBody String json) {
        return jsonService.minifyJson(json);  // Delegate to the service
    }

    @PostMapping("/convert/xml")
    public String convertJsonToXml(@RequestBody String json) {
        return jsonService.convertJson(json, ConversionType.XML);  // Delegate to the service (assuming Format.XML exists)
    }

    @PostMapping("/convert/yaml")
    public String convertJsonToYaml(@RequestBody String json) {
        return jsonService.convertJson(json, ConversionType.YAML);  // Delegate to the service (assuming Format.YAML exists)
    }

    @PostMapping("/convert/csv")
    public String convertJsonToCsv(@RequestBody String json) {
        return jsonService.convertJson(json, ConversionType.CSV);  // Delegate to the service (assuming Format.CSV exists)
    }

    @PostMapping("/sort")
    public String sortJson(@RequestBody String json) {
        return jsonService.sortJson(json);  // Delegate to the service
    }
}
