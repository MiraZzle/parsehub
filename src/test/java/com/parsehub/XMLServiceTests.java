package com.parsehub;

import com.parsehub.util.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.parsehub.service.XmlService;

class XmlServiceTest {

    private
    XmlService xmlService;

    @BeforeEach
    void setUp() {
        xmlService = new XmlService();
    }

    @Test
    void validateXml_shouldReturnValid_whenXmlIsWellFormed() {
        String validXml = """
            <person>
                <name>John Doe</name>
                <age>30</age>
                <address>
                    <street>Main St</street>
                    <city>New York</city>
                </address>
            </person>
            """;

        ValidationResult result = xmlService.validateXml(validXml);
        assertTrue(result.isValid());
        assertTrue(result.getErrorMessages().isEmpty());
    }

    @Test
    void validateXml_shouldReturnInvalid_whenXmlIsMalformed() {
        String invalidXml = """
            <person>
                <name>John Doe</name>
                <age>30</age>
                <address>
                    <street>Main St</street>
                    <city>New York
                </address>
            </person>
            """;

        ValidationResult result = xmlService.validateXml(invalidXml);
        assertFalse(result.isValid());
        assertFalse(result.getErrorMessages().isEmpty());
    }

    @Test
    void convertXmlToJson_shouldConvertProperly() {
        String validXml = """
            <person>
                <name>John Doe</name>
                <age>30</age>
                <address>
                    <street>Main St</street>
                    <city>New York</city>
                </address>
            </person>
            """;

        String jsonResult = xmlService.convertXmlToJson(validXml);
        assertTrue(jsonResult.contains("\"name\""));
        assertTrue(jsonResult.contains("\"John Doe\""));
        assertTrue(jsonResult.contains("\"address\""));
    }

    @Test
    void convertXmlToYaml_shouldConvertProperly() {
        String validXml = """
            <person>
                <name>John Doe</name>
                <age>30</age>
                <address>
                    <street>Main St</street>
                    <city>New York</city>
                </address>
            </person>
            """;

        String yamlResult = xmlService.convertXmlToYaml(validXml);
        assertTrue(yamlResult.contains("name: John Doe"));
        assertTrue(yamlResult.contains("address:"));
    }

    @Test
    void convertXmlToCsv_shouldThrowError_whenXmlIsNotArrayLike() {
        String invalidXml = """
            <person>
                <name>John Doe</name>
                <age>30</age>
            </person>
            """;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            xmlService.convertXmlToCsv(invalidXml);
        });

        assertEquals("XML must represent an array-like structure for CSV conversion", exception.getMessage());
    }

    @Test
    void minifyXml_shouldMinifyProperly() {
        String formattedXml = """
            <person>
                <name>John Doe</name>
                <age>30</age>
                <address>
                    <street>Main St</street>
                    <city>New York</city>
                </address>
            </person>
            """;

        String minifiedResult = xmlService.minifyXml(formattedXml);
        assertTrue(minifiedResult.contains("<ObjectNode><name>John Doe</name><age>30</age><address><street>Main St</street><city>New York</city></address></ObjectNode>"));
    }

    @Test
    void minifyXml_shouldReturnError_whenXmlIsInvalid() {
        String invalidXml = """
            <person>
                <name>John Doe</name>
                <age>30
                <address>
                    <street>Main St</street>
                    <city>New York</city>
                </address>
            </person>
            """;

        String minifiedResult = xmlService.minifyXml(invalidXml);
        assertTrue(minifiedResult.contains("Invalid XML:"));
    }
}
