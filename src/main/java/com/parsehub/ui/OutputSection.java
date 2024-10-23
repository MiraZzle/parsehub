package com.parsehub.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OutputSection extends VerticalLayout {

    private final TextArea outputArea;
    private final Button copyButton;

    public OutputSection() {
        Span outputLabel = createLabel("Output");
        copyButton = createCopyButton();
        outputArea = createTextArea();

        HorizontalLayout controls = createControls(outputLabel, copyButton);

        add(controls, outputArea);
        configureLayoutSettings();
        setClassName("output-section");
    }

    /**
     * Creates a label for the section.
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
     * Creates the text area for displaying output.
     *
     * @return The configured TextArea component.
     */
    private TextArea createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setPlaceholder("Output will appear here...");
        textArea.setWidthFull();
        return textArea;
    }

    /**
     * Creates a horizontal layout to hold the label and copy button.
     *
     * @param label The output label.
     * @param copyButton The copy button.
     * @return The configured HorizontalLayout component.
     */
    private HorizontalLayout createControls(Span label, Button copyButton) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("text-area-controls");
        layout.add(label, copyButton);
        return layout;
    }

    /**
     * Configures layout-specific settings such as spacing.
     */
    private void configureLayoutSettings() {
        setSpacing(true);
    }

    /**
     * Sets the value for the output text area.
     *
     * @param value The value to be displayed in the output area.
     */
    public void setOutputValue(String value) {
        outputArea.setValue(value);
    }
}
