package com.parsehub.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;

public class InputSection extends VerticalLayout {

    private final TextArea inputArea;
    private final RadioButtonGroup<String> formatSelector;
    private final Button copyButton;

    public InputSection() {
        Span inputLabel = createLabel("Input");
        copyButton = createCopyButton();
        formatSelector = createFormatSelector();
        inputArea = createTextArea();

        HorizontalLayout inputControls = createInputControls(inputLabel, formatSelector, copyButton);
        add(inputControls, inputArea);
        configureLayoutSettings();
        setClassName("input-section");
    }

    /**
     * Creates a label component.
     *
     * @param text The label text.
     * @return The configured Span component.
     */
    private Span createLabel(String text) {
        Span label = new Span(text);
        label.setClassName("section-label");  // CSS class for styling
        return label;
    }

    /**
     * Creates the copy button.
     *
     * @return The configured Button component.
     */
    private Button createCopyButton() {
        Button button = new Button("Copy");
        button.setClassName("copy-button");
        return button;
    }

    /**
     * Creates the format selector (RadioButtonGroup) with options.
     *
     * @return The configured RadioButtonGroup component.
     */
    private RadioButtonGroup<String> createFormatSelector() {
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setItems("JSON", "XML", "YAML");
        radioGroup.setValue("JSON");  // Default value
        return radioGroup;
    }

    /**
     * Creates a text area for input data.
     *
     * @return The configured TextArea component.
     */
    private TextArea createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setPlaceholder("Enter your JSON here...");
        textArea.setWidthFull();
        return textArea;
    }

    /**
     * Creates a horizontal layout to hold input controls.
     *
     * @param label The input label.
     * @param formatSelector The format selector.
     * @param copyButton The copy button.
     * @return The configured HorizontalLayout component.
     */
    private HorizontalLayout createInputControls(Span label, RadioButtonGroup<String> formatSelector, Button copyButton) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("text-area-controls");
        layout.add(label, formatSelector, copyButton);
        return layout;
    }

    /**
     * Configures layout-specific settings such as spacing and padding.
     */
    private void configureLayoutSettings() {
        setSpacing(true);
        setPadding(true);
    }

    // Public accessors

    /**
     * Returns the currently selected format from the RadioButtonGroup.
     *
     * @return The selected format as a String.
     */
    public String getSelectedFormat() {
        return formatSelector.getValue();
    }

    /**
     * Returns the current value entered in the input text area.
     *
     * @return The input data as a String.
     */
    public String getInputValue() {
        return inputArea.getValue();
    }

    /**
     * Provides access to the format selector for further customization if needed.
     *
     * @return The format selector component.
     */
    public RadioButtonGroup<String> getFormatSelector() {
        return formatSelector;
    }
}
