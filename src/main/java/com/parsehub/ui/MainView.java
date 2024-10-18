package com.parsehub.ui;

import com.parsehub.service.JsonService;
import com.parsehub.service.XmlService;
import com.parsehub.util.Format;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Route("") // Vaadin route
public class MainView extends VerticalLayout {

    private final JsonService jsonService;
    private final XmlService xmlService;

    // Injecting services via constructor
    @Autowired
    public MainView(JsonService jsonService, XmlService xmlService) {
        this.jsonService = jsonService;
        this.xmlService = xmlService;

        // Input TextArea
        TextArea inputArea = new TextArea("Input");
        inputArea.setPlaceholder("Enter JSON or XML here...");
        inputArea.setWidthFull();
        inputArea.setHeight("300px");

        // Output TextArea
        TextArea outputArea = new TextArea("Output");
        outputArea.setPlaceholder("Output will be shown here...");
        outputArea.setWidthFull();
        outputArea.setHeight("300px");

        // Dropdown to select format (JSON/XML)
        ComboBox<String> formatSelect = new ComboBox<>("Select Format");
        formatSelect.setItems("JSON", "XML");
        formatSelect.setValue("JSON"); // Default selection

        // Dropdown for indentation level
        Select<Integer> indentSelect = new Select<>();
        indentSelect.setItems(2, 4);
        indentSelect.setLabel("Indentation");
        indentSelect.setValue(2); // Default value

        // Validate button
        Button validateButton = new Button("Validate", event -> {
            String input = inputArea.getValue();
            String format = formatSelect.getValue();
            String validationResult = validateData(input, format);
            outputArea.setValue(validationResult);
        });

        // Format/Beautify button
        Button formatButton = new Button("Format / Beautify", event -> {
            String input = inputArea.getValue();
            String format = formatSelect.getValue();
            int indent = indentSelect.getValue();
            String formattedData = formatData(input, format, indent);
            inputArea.setValue(formattedData); // Update input with formatted data
            outputArea.setValue(formattedData); // Show formatted data in output
        });

        // Minify button
        Button minifyButton = new Button("Minify", event -> {
            String input = inputArea.getValue();
            String format = formatSelect.getValue();
            String minifiedData = minifyData(input, format);
            inputArea.setValue(minifiedData); // Update input with minified data
            outputArea.setValue(minifiedData); // Show minified data in output
        });

        // Horizontal layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(validateButton, formatButton, minifyButton);

        // Add components to the layout
        add(formatSelect, inputArea, buttonLayout, outputArea, indentSelect);
    }

    // Validate JSON or XML using the appropriate service
    private String validateData(String data, String format) {
        if ("JSON".equals(format)) {
            var validationResult = jsonService.validateJson(data);
            return validationResult.isValid() ? "Valid JSON" : "Invalid JSON: " + validationResult.getErrorMessages();
        } else if ("XML".equals(format)) {
            var validationResult = xmlService.validateXml(data);
            return validationResult.isValid() ? "Valid XML" : "Invalid XML: " + validationResult.getErrorMessages();
        }
        return "Unsupported format";
    }

    // Format JSON or XML using the appropriate service
    private String formatData(String data, String format, int indent) {
        if ("JSON".equals(format)) {
            return jsonService.formatJson(data, Format.valueOf("SPACE_" + indent));
        } else if ("XML".equals(format)) {
            return xmlService.formatXml(data, Format.valueOf("SPACE_" + indent));
        }
        return "Unsupported format";
    }

    // Minify JSON or XML using the appropriate service
    private String minifyData(String data, String format) {
        if ("JSON".equals(format)) {
            return jsonService.minifyJson(data);
        } else if ("XML".equals(format)) {
            return xmlService.minifyXml(data);
        }
        return "Unsupported format";
    }
}
