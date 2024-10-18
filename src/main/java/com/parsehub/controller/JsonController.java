package com.parsehub.controller;

import com.parsehub.service.JsonService;
import com.parsehub.util.ConversionType;
import com.parsehub.util.Format;
import com.parsehub.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

        return jsonService.validateJson(json);
    }

    @PostMapping("/format/{format}")
    public String formatJson(@RequestBody String json, @PathVariable String format) {
        Format formatEnum = Format.valueOf(format.toUpperCase());
        return jsonService.formatJson(json, formatEnum);
    }

    @PostMapping("/minify")
    public String minifyJson(@RequestBody String json) {
        return jsonService.minifyJson(json);
    }

    @PostMapping("/convert/xml")
    public String convertJsonToXml(@RequestBody String json) {
        return jsonService.convertJson(json, ConversionType.XML);
    }

    @PostMapping("/convert/yaml")
    public String convertJsonToYaml(@RequestBody String json) {
        return jsonService.convertJson(json, ConversionType.YAML);
    }

    @PostMapping("/convert/csv")
    public String convertJsonToCsv(@RequestBody String json) {
        return jsonService.convertJson(json, ConversionType.CSV);
    }

    @PostMapping("/sort")
    public String sortJson(@RequestBody String json) {
        return jsonService.sortJson(json);
    }
}
