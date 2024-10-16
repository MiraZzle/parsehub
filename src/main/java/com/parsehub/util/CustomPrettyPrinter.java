package com.parsehub.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;

public class CustomPrettyPrinter extends DefaultPrettyPrinter {
    private final int indentSize;

    public CustomPrettyPrinter(int indentSize) {
        this.indentSize = indentSize;
        this._objectFieldValueSeparatorWithSpaces = ": ";  // Adjust as needed
        this.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        this.indentObjectsWith(new DefaultIndenter(" ".repeat(indentSize), DefaultIndenter.SYS_LF));
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new CustomPrettyPrinter(indentSize);
    }
}
