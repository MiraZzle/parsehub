package com.parsehub.controller;

import com.parsehub.service.XmlService;
import com.parsehub.util.ConversionType;
import com.parsehub.util.Format;
import com.parsehub.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/xml")
public class XmlController {

    private final XmlService xmlService;

    @Autowired
    public XmlController(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @PostMapping("/validate")
    public ValidationResult validateXml(@RequestBody String xml) {
        System.out.println("Request received: " + xml);

        return xmlService.validateXml(xml);
    }

    @PostMapping("/format/{format}")
    public String formatXml(@RequestBody String xml, @PathVariable String format) {
        Format formatEnum = Format.valueOf(format.toUpperCase());
        return xmlService.formatXml(xml, formatEnum);
    }

    @PostMapping("/minify")
    public String minifyXml(@RequestBody String xml) {
        return xmlService.minifyXml(xml);
    }

    @PostMapping("/convert/json")
    public String convertXmlToJson(@RequestBody String xml) {
        return xmlService.convertXmlToJson(xml);
    }

    @PostMapping("/convert/yaml")
    public String convertXmlToYaml(@RequestBody String xml) {
        return xmlService.convertXmlToYaml(xml);
    }

    @PostMapping("/convert/csv")
    public String convertXmlToCsv(@RequestBody String xml) {
        return xmlService.convertXmlToCsv(xml);
    }
}
