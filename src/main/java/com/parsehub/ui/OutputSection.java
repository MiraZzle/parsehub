package com.parsehub.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class OutputSection extends VerticalLayout {

    private final TextArea outputArea;
    private final Button copyButton;
    private final Anchor downloadAnchor;

    public OutputSection() {
        Span outputLabel = createLabel("Output");
        copyButton = createCopyButton();
        outputArea = createTextArea();
        downloadAnchor = createDownloadAnchor();

        HorizontalLayout controls = createControls(outputLabel, downloadAnchor);

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
     * Creates a horizontal layout to hold the label and download button.
     *
     * @param label The output label.
     * @param downloadAnchor The download anchor with the button.
     * @return The configured HorizontalLayout component.
     */
    private HorizontalLayout createControls(Span label, Anchor downloadAnchor) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("text-area-controls");
        layout.add(label, downloadAnchor);
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
        configureDownloadAnchor();  // Update the download link
    }

    /**
     * Configures the download button to allow downloading the output as a file.
     * This method regenerates the download link based on the current output value.
     */
    private void configureDownloadAnchor() {
        String outputData = outputArea.getValue();
        StreamResource resource = new StreamResource("output.txt",
                () -> new ByteArrayInputStream(outputData.getBytes(StandardCharsets.UTF_8)));

        downloadAnchor.setHref(resource);
        downloadAnchor.getElement().setAttribute("download", "output.txt");  // Forces the file to be downloaded
    }

    /**
     * Creates the download anchor, wrapping the download button inside an anchor element
     * to handle file downloads when the button is clicked.
     *
     * @return The configured Anchor component.
     */
    private Anchor createDownloadAnchor() {
        Button downloadButton = new Button("Download File");
        downloadButton.setEnabled(false);
        downloadButton.setClassName("download-button");

        // Enable download when there's output
        outputArea.addValueChangeListener(event -> {
            if (!event.getValue().isEmpty()) {
                downloadButton.setEnabled(true);
            } else {
                downloadButton.setEnabled(false);
            }
        });

        Anchor anchor = new Anchor();
        anchor.add(downloadButton);
        anchor.setTarget("_self");
        return anchor;
    }
}
cd 