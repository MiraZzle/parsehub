package com.parsehub.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.dependency.CssImport;

@CssImport("./styles/styles.css")
public class InputSection extends VerticalLayout {
    private TextArea inputArea;
    private RadioButtonGroup<String> formatSelector;

    public InputSection() {
        formatSelector = new RadioButtonGroup<>();
        formatSelector.setLabel("Input");
        formatSelector.setItems("JSON", "XML", "YAML");

        inputArea = new TextArea("Enter your data...");
        inputArea.setWidthFull();
        inputArea.setHeight("300px");

        Button copyButton = new Button("Copy");

        add(formatSelector, inputArea, copyButton);
        setSpacing(true);
        setPadding(true);
        setSizeFull();
    }

    public String getInputValue() {
        return inputArea.getValue();
    }

    public String getSelectedFormat() {
        return formatSelector.getValue();
    }
}
