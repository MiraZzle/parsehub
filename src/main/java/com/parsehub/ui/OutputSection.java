package com.parsehub.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OutputSection extends VerticalLayout {

    private TextArea outputArea;
    private Button copyButton;


    public OutputSection() {
        setClassName("output-section"); // Set the CSS class

        Span inputLabel = new Span("Output");
        inputLabel.setClassName("section-label");  // Optional: CSS class for styling

        copyButton = new Button("Copy");
        copyButton.setClassName("copy-button");

        HorizontalLayout controls = new HorizontalLayout();
        controls.setClassName("text-area-controls");
        controls.add(inputLabel, copyButton);

        outputArea = new TextArea();
        outputArea.setPlaceholder("Output will appear here...");
        outputArea.setWidthFull();

        add(controls, outputArea);
        setSpacing(true);
    }

    public void setOutputValue(String value) {
        outputArea.setValue(value);
    }
}
