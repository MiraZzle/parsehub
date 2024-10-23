package com.parsehub.util;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;

/**
 * Custom pretty printer for JSON indentation.
 */
public class CustomPrettyPrinter extends DefaultPrettyPrinter {
    private final int indentSize;

    /**
     * Constructs a new CustomPrettyPrinter with a specified indentation size.
     *
     * @param indentSize the number of spaces to use for indentation
     */
    public CustomPrettyPrinter(int indentSize) {
        this.indentSize = indentSize;
        this._objectFieldValueSeparatorWithSpaces = ": ";  // Adjust as needed
        this.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        this.indentObjectsWith(new DefaultIndenter(" ".repeat(indentSize), DefaultIndenter.SYS_LF));
    }

    /**
     * Creates a new instance of the CustomPrettyPrinter, with the same indentation configuration.
     *
     * @return a new CustomPrettyPrinter instance with the same indentation size.
     */
    @Override
    public DefaultPrettyPrinter createInstance() {
        return new CustomPrettyPrinter(indentSize);
    }
}
