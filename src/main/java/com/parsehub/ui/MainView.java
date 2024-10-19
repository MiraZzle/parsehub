package com.parsehub.ui;

import com.parsehub.service.JsonService;
import com.parsehub.service.XmlService;
import com.parsehub.service.YamlService;
import com.parsehub.util.ValidationResult;
import com.parsehub.util.Format;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.dependency.CssImport;
import com.parsehub.util.ConversionType;

@SpringComponent
@Route("")
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    private final JsonService jsonService;
    private final XmlService xmlService;
    private final YamlService yamlService;

    private InputSection inputSection;
    private ButtonsSection buttonsSection;
    private OutputSection outputSection;

    @Autowired
    public MainView(JsonService jsonService, XmlService xmlService, YamlService yamlService) {
        this.jsonService = jsonService;
        this.xmlService = xmlService;
        this.yamlService = yamlService;

        inputSection = new InputSection();
        buttonsSection = new ButtonsSection();
        outputSection = new OutputSection();

        HorizontalLayout mainLayout = new HorizontalLayout(inputSection, buttonsSection, outputSection);
        mainLayout.setSizeFull();

        add(mainLayout);
        setSizeFull();

        // Wire button events
        buttonsSection.getValidateButton().addClickListener(event -> validateData());
        buttonsSection.getFormatButton().addClickListener(event -> formatData());
        buttonsSection.getMinifyButton().addClickListener(event -> minifyData());
        buttonsSection.getConvertButton().addClickListener(event -> convertData());
    }

    private void validateData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();
        ValidationResult result;

        if ("JSON".equalsIgnoreCase(format)) {
            result = jsonService.validateJson(input);
        } else if ("XML".equalsIgnoreCase(format)) {
            result = xmlService.validateXml(input);
        } else if ("YAML".equalsIgnoreCase(format)) {
            result = yamlService.validateYaml(input);
        } else {
            outputSection.setOutputValue("Unsupported format");
            return;
        }

        outputSection.setOutputValue(result.isValid() ? "Valid " + format : "Invalid " + format + "\nErrors: " + result.getErrorMessages());
    }

    private void formatData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();
        Format indentationFormat = Format.valueOf(buttonsSection.getSelectedIndentation());

        String result;

        if ("JSON".equalsIgnoreCase(format)) {
            result = jsonService.formatJson(input, indentationFormat);
        } else if ("XML".equalsIgnoreCase(format)) {
            result = xmlService.formatXml(input, indentationFormat);
        } else if ("YAML".equalsIgnoreCase(format)) {
            result = yamlService.formatYaml(input, indentationFormat);
        } else {
            outputSection.setOutputValue("Unsupported format");
            return;
        }

        outputSection.setOutputValue(result);
    }

    private void minifyData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();

        String result;

        if ("JSON".equalsIgnoreCase(format)) {
            result = jsonService.minifyJson(input);
        } else if ("XML".equalsIgnoreCase(format)) {
            result = xmlService.minifyXml(input);
        } else if ("YAML".equalsIgnoreCase(format)) {
            result = yamlService.minifyYaml(input);
        } else {
            outputSection.setOutputValue("Unsupported format");
            return;
        }

        outputSection.setOutputValue(result);
    }

    private void convertData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();  // Current format selected by user (JSON/XML/YAML)
        ConversionType targetFormat = ConversionType.valueOf(buttonsSection.getSelectedConversionType().toUpperCase()); // Convert target format

        String result;

        try {
            switch (format.toUpperCase()) {
                case "JSON":
                    result = jsonService.convertData(input, targetFormat); // Calls service method based on target format
                    break;
                case "XML":
                    result = xmlService.convertData(input, targetFormat);  // Calls service method based on target format
                    break;
                case "YAML":
                    result = yamlService.convertData(input, targetFormat); // Calls service method based on target format
                    break;
                default:
                    result = "Unsupported input format";
            }
            outputSection.setOutputValue(result);  // Show converted result in the output section
        } catch (Exception e) {
            outputSection.setOutputValue("Conversion error: " + e.getMessage());
        }
    }
}
