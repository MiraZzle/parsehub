package com.parsehub.ui;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class OutputSection extends VerticalLayout {

    private TextArea outputArea;

    public OutputSection() {
        outputArea = new TextArea("Output");
        outputArea.setWidthFull();
        outputArea.setHeight("300px");

        add(outputArea);
        setSpacing(true);
        setPadding(true);
        setSizeFull();
    }

    public void setOutputValue(String value) {
        outputArea.setValue(value);
    }
}
