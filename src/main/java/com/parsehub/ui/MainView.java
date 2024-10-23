package com.parsehub.ui;

import com.parsehub.service.JsonService;
import com.parsehub.service.XmlService;
import com.parsehub.service.YamlService;
import com.parsehub.util.ValidationResult;
import com.parsehub.util.Format;
import com.parsehub.util.ConversionType;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@Route("")
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    private final JsonService jsonService;
    private final XmlService xmlService;
    private final YamlService yamlService;

    private final InputSection inputSection;
    private final ButtonsSection buttonsSection;
    private final OutputSection outputSection;

    @Autowired
    public MainView(JsonService jsonService, XmlService xmlService, YamlService yamlService) {
        this.jsonService = jsonService;
        this.xmlService = xmlService;
        this.yamlService = yamlService;

        inputSection = new InputSection();
        buttonsSection = new ButtonsSection();
        outputSection = new OutputSection();

        configureLayout();

        inputSection.getFormatSelector().addValueChangeListener(event -> adjustButtonsVisibility(event.getValue()));
        wireButtonActions();
    }

    /**
     * Configures the main layout by adding sections and setting layout properties.
     */
    private void configureLayout() {
        HorizontalLayout mainLayout = new HorizontalLayout(inputSection, buttonsSection, outputSection);
        mainLayout.setSizeFull();  // Set full size for responsive design
        add(mainLayout);
        setSizeFull();
    }

    /**
     * Wires actions to each button in the buttons section.
     */
    private void wireButtonActions() {
        buttonsSection.getValidateButton().addClickListener(event -> validateData());
        buttonsSection.getFormatButton().addClickListener(event -> formatData());
        buttonsSection.getMinifyButton().addClickListener(event -> minifyData());
        buttonsSection.getConvertButton().addClickListener(event -> convertData());
        buttonsSection.getSortButton().addClickListener(event -> sortData());
    }

    /**
     * Adjusts the visibility of buttons and options based on the selected format.
     *
     * @param selectedFormat The format selected in the InputSection.
     */
    private void adjustButtonsVisibility(String selectedFormat) {
        boolean isJson = "JSON".equalsIgnoreCase(selectedFormat);

        // Show or hide the format button, indentation combo box, and sort button based on the format
        buttonsSection.getFormatButton().setVisible(isJson);
        buttonsSection.getIndentationComboBox().setVisible(isJson);
        buttonsSection.getSortButton().setVisible(isJson);
    }

    /**
     * Sorts the JSON data if applicable and updates the output section.
     */
    private void sortData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();
        String result;

        if ("JSON".equalsIgnoreCase(format)) {
            result = jsonService.sortJson(input);
        } else {
            outputSection.setOutputValue("Unsupported format");
            return;
        }

        outputSection.setOutputValue(result);
    }

    /**
     * Validates the input data based on the selected format and displays the result in the output section.
     */
    private void validateData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();
        ValidationResult result;

        switch (format.toUpperCase()) {
            case "JSON":
                result = jsonService.validateJson(input);
                break;
            case "XML":
                result = xmlService.validateXml(input);
                break;
            case "YAML":
                result = yamlService.validateYaml(input);
                break;
            default:
                outputSection.setOutputValue("Unsupported format");
                return;
        }

        outputSection.setOutputValue(result.isValid() ? "Valid " + format : "Invalid " + format + "\nErrors: " + result.getErrorMessages());
    }

    /**
     * Formats the input JSON data according to the selected indentation and updates the output section.
     */
    private void formatData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();
        String selectedIndentation = buttonsSection.getSelectedIndentation();
        Format indentationFormat;

        try {
            int indentation = Integer.parseInt(selectedIndentation);
            indentationFormat = Format.valueOf("SPACE_" + indentation);
        } catch (NumberFormatException e) {
            indentationFormat = Format.valueOf(selectedIndentation);
        }

        String result;

        if ("JSON".equalsIgnoreCase(format)) {
            result = jsonService.formatJson(input, indentationFormat);
        } else {
            outputSection.setOutputValue("Unsupported format");
            return;
        }

        outputSection.setOutputValue(result);
    }

    /**
     * Minifies the input data based on the selected format and updates the output section.
     */
    private void minifyData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();
        String result;

        switch (format.toUpperCase()) {
            case "JSON":
                result = jsonService.minifyJson(input);
                break;
            case "XML":
                result = xmlService.minifyXml(input);
                break;
            case "YAML":
                result = yamlService.minifyYaml(input);
                break;
            default:
                outputSection.setOutputValue("Unsupported format");
                return;
        }

        outputSection.setOutputValue(result);
    }

    /**
     * Converts the input data from one format to another and updates the output section.
     */
    private void convertData() {
        String input = inputSection.getInputValue();
        String format = inputSection.getSelectedFormat();
        ConversionType targetFormat = ConversionType.valueOf(buttonsSection.getSelectedConversionType().toUpperCase());

        String result;

        try {
            result = switch (format.toUpperCase()) {
                case "JSON" -> jsonService.convertData(input, targetFormat);
                case "XML" -> xmlService.convertData(input, targetFormat);
                case "YAML" -> yamlService.convertData(input, targetFormat);
                default -> "Unsupported input format";
            };
            outputSection.setOutputValue(result);
        } catch (Exception e) {
            outputSection.setOutputValue("Conversion error: " + e.getMessage());
        }
    }
}
