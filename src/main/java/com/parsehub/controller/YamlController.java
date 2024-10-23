package com.parsehub.controller;

import com.parsehub.service.YamlService;
import com.parsehub.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.parsehub.util.Format;

@RestController
@RequestMapping("/api/v1/yaml")
public class YamlController {

    private final YamlService yamlService;

    @Autowired
    public YamlController(YamlService yamlService) {
        this.yamlService = yamlService;
    }

    @PostMapping("/validate")
    public ValidationResult validateYaml(@RequestBody String yaml) {
        return yamlService.validateYaml(yaml);
    }

    @PostMapping("/minify")
    public String minifyYaml(@RequestBody String yaml) {
        return yamlService.minifyYaml(yaml);
    }

    @PostMapping("/convert/json")
    public String convertYamlToJson(@RequestBody String yaml) {
        return yamlService.convertYamlToJson(yaml);
    }

    @PostMapping("/convert/xml")
    public String convertYamlToXml(@RequestBody String yaml) {
        return yamlService.convertYamlToXml(yaml);
    }

    @PostMapping("/convert/csv")
    public String convertYamlToCsv(@RequestBody String yaml) {
        return yamlService.convertYamlToCsv(yaml);
    }
}

