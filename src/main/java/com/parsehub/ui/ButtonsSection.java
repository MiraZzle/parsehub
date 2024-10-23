package com.parsehub.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ButtonsSection extends VerticalLayout {

    private final Button validateButton;
    private final Button formatButton;
    private final Button minifyButton;
    private final Button sortButton;
    private final ComboBox<String> indentationComboBox;
    private final ComboBox<String> conversionComboBox;
    private final Button convertButton;

    public ButtonsSection() {
        validateButton = createButton("Validate");
        formatButton = createButton("Format");
        minifyButton = createButton("Minify");
        sortButton = createButton("Sort Data");
        convertButton = createButton("Convert Data");

        indentationComboBox = createComboBox("Indentation", "2", "4");
        indentationComboBox.setValue("2");

        conversionComboBox = createComboBox("Convert to", "JSON", "XML", "YAML", "CSV");
        conversionComboBox.setValue("JSON");

        // add all components to the layout
        addComponentsToLayout();
        setSpacing(true);
        setClassName("buttons-section");
    }

    /**
     * Helper method to create a button with a given label.
     *
     * @param label the label of the button
     * @return the created button
     */
    private Button createButton(String label) {
        return new Button(label);
    }

    /**
     * Helper method to create a ComboBox with a label and given items.
     *
     * @param label the label of the ComboBox
     * @param items the items to display in the ComboBox
     * @return the created ComboBox
     */
    private ComboBox<String> createComboBox(String label, String... items) {
        ComboBox<String> comboBox = new ComboBox<>(label);
        comboBox.setItems(items);
        return comboBox;
    }

    /**
     * Adds all components to the layout in the correct order.
     */
    private void addComponentsToLayout() {
        add(validateButton, minifyButton, sortButton, indentationComboBox,
                formatButton, conversionComboBox, convertButton);
    }

    /**
     * Returns the sort button used for sorting the data.
     *
     * @return the sort button
     */
    public Button getSortButton() {
        return sortButton;
    }

    /**
     * Returns the validate button used for validating data.
     *
     * @return the validate button
     */
    public Button getValidateButton() {
        return validateButton;
    }

    /**
     * Returns the format button used for formatting data.
     *
     * @return the format button
     */
    public Button getFormatButton() {
        return formatButton;
    }

    /**
     * Returns the minify button used for minifying data.
     *
     * @return the minify button
     */
    public Button getMinifyButton() {
        return minifyButton;
    }

    /**
     * Returns the selected value from the indentation ComboBox.
     *
     * @return the selected indentation as a String
     */
    public String getSelectedIndentation() {
        return indentationComboBox.getValue();
    }

    /**
     * Returns the selected conversion type from the conversion ComboBox.
     *
     * @return the selected conversion type as a String
     */
    public String getSelectedConversionType() {
        return conversionComboBox.getValue();
    }

    /**
     * Returns the convert button used for converting data between formats.
     *
     * @return the convert button
     */
    public Button getConvertButton() {
        return convertButton;
    }

    /**
     * Returns the ComboBox used for selecting the indentation level.
     *
     * @return the indentation ComboBox
     */
    public ComboBox<String> getIndentationComboBox() {
        return indentationComboBox;
    }
}
