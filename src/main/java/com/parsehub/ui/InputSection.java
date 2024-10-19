package com.parsehub.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.component.html.Span;

public class InputSection extends VerticalLayout {
    private TextArea inputArea;
    private RadioButtonGroup<String> formatSelector;
    private Button copyButton;

    public InputSection() {
        setClassName("input-section");

        Span inputLabel = new Span("Input");
        inputLabel.setClassName("input-label");  // Optional: CSS class for styling

        copyButton = new Button("Copy");
        copyButton.setClassName("copy-button");

        formatSelector = new RadioButtonGroup<>();
        formatSelector.setItems("JSON", "XML", "YAML");
        formatSelector.setValue("JSON");

        inputArea = new TextArea();
        inputArea.setPlaceholder("Enter your JSON here...");
        inputArea.setWidthFull();

        HorizontalLayout inputControls = new HorizontalLayout();
        inputControls.setClassName("input-controls-section");
        inputControls.add(inputLabel, formatSelector, copyButton);

        add(inputControls, inputArea);

        setSpacing(true);
        setPadding(true);
    }

    public String getInputValue() {
        return inputArea.getValue();
    }

    public String getSelectedFormat() {
        return formatSelector.getValue();
    }
}
