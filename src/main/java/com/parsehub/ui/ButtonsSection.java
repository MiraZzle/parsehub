package com.parsehub.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ButtonsSection extends VerticalLayout {

    private Button validateButton;
    private Button formatButton;
    private Button minifyButton;
    private Button sortButton;
    private ComboBox<String> indentationComboBox;

    private ComboBox<String> conversionComboBox; // Conversion format selection
    private Button convertButton; // Button to trigger conversion

    public ButtonsSection() {
        // Initialize existing buttons
        validateButton = new Button("Validate");
        formatButton = new Button("Format");
        minifyButton = new Button("Minify");
        convertButton = new Button("Convert Data");
        sortButton = new Button("Sort Data");

        // Initialize ComboBox for indentation selection
        indentationComboBox = new ComboBox<>("Indentation");
        indentationComboBox.setItems("2", "4");
        indentationComboBox.setValue("2"); // Default value

        // Initialize ComboBox for conversion types
        conversionComboBox = new ComboBox<>("Convert to");
        conversionComboBox.setItems("JSON", "XML", "YAML", "CSV"); // Available conversion formats
        conversionComboBox.setValue("JSON"); // Default to JSON

        // Initialize Convert button

        // Add everything to the layout
        add(validateButton, minifyButton, sortButton, indentationComboBox, formatButton, conversionComboBox, convertButton);
        setSpacing(true);
        setClassName("buttons-section"); // Optional styling class for ButtonsSection
    }

    public Button getSortButton() {
        return sortButton;
    }

    public Button getValidateButton() {
        return validateButton;
    }

    public Button getFormatButton() {
        return formatButton;
    }

    public Button getMinifyButton() {
        return minifyButton;
    }

    public String getSelectedIndentation() {
        return indentationComboBox.getValue();
    }

    public String getSelectedConversionType() {
        return conversionComboBox.getValue();
    }

    public Button getConvertButton() {
        return convertButton;
    }

    public ComboBox<String> getIndentationComboBox() {
        return indentationComboBox;
    }
}
