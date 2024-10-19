package com.parsehub.ui;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OutputSection extends VerticalLayout {

    private TextArea outputArea;

    public OutputSection() {
        setClassName("output-section"); // Set the CSS class

        outputArea = new TextArea();
        outputArea.setPlaceholder("Output will appear here...");
        outputArea.setWidthFull();

        add(outputArea);
        setSpacing(true);
    }

    public void setOutputValue(String value) {
        outputArea.setValue(value);
    }
}
