package com.parsehub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parsehub.util.ConversionType;
import com.parsehub.util.Format;
import com.parsehub.util.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.parsehub.service.JsonService;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {

    private JsonService jsonService;

    @BeforeEach
    void setUp() {
        jsonService = new JsonService();
    }

    @Test
    void testValidateJson_ValidJson() {
        String validJson = "{\"name\": \"John\", \"age\": 30}";
        ValidationResult result = jsonService.validateJson(validJson);

        assertTrue(result.isValid());
        assertTrue(result.getErrorMessages().isEmpty());
    }

    @Test
    void testValidateJson_InvalidJson() {
        String invalidJson = "{\"name\": \"John\", \"age\": }"; // Invalid JSON

        ValidationResult result = jsonService.validateJson(invalidJson);

        assertFalse(result.isValid());
        assertFalse(result.getErrorMessages().isEmpty());
        assertEquals("Invalid JSON: Unexpected character ('}' (code 125)): expected a valid ", result.getErrorMessages().get(0).substring(0, 70));
    }

    @Test
    void testFormatJson_ValidJson() {
        String validJson = "{\"name\":\"John\",\"age\":30}";
        String formattedJson = jsonService.formatJson(validJson, Format.SPACE_2);

        String expectedJson = """
                {
                  "name": "John",
                  "age": 30
                }
                """;
        assertEquals(expectedJson.trim(), formattedJson.trim());
    }

    @Test
    void testMinifyJson_ValidJson() {
        String formattedJson = """
                {
                  "name": "John",
                  "age": 30
                }
                """;
        String minifiedJson = jsonService.minifyJson(formattedJson);

        String expectedMinifiedJson = "{\"name\":\"John\",\"age\":30}";
        assertEquals(expectedMinifiedJson, minifiedJson);
    }

    @Test
    void testConvertJsonToXml_ValidJson() throws JsonProcessingException {
        String json = "{\"name\":\"John\",\"age\":30}";
        String xml = jsonService.convertData(json, ConversionType.XML);

        String expectedXml = """
                <ObjectNode>
                  <name>John</name>
                  <age>30</age>
                </ObjectNode>
                """;

        assertTrue(xml.contains("<ObjectNode>"));
        assertTrue(xml.contains("<name>John</name>"));
        assertTrue(xml.contains("<age>30</age>"));
    }

    @Test
    void testConvertJsonToYaml_ValidJson() {
        String json = "{\"name\":\"John\",\"age\":30}";
        String yaml = jsonService.convertData(json, ConversionType.YAML);

        String expectedYaml = """
                name: John
                age: 30
                """;
        assertEquals(expectedYaml.trim(), yaml.trim());
    }

    @Test
    void testConvertJsonToCsv_ValidJson() throws JsonProcessingException {
        String json = "[{\"name\":\"John\",\"age\":30}]";
        String csv = jsonService.convertData(json, ConversionType.CSV);

        String expectedCsv = """
                name,age
                John,30
                """;
        assertEquals(expectedCsv.trim(), csv.trim());
    }

    @Test
    void testSortJson_ValidJson() {
        String unsortedJson = "{\"b\": 2, \"a\": 1, \"c\": 3}";
        String sortedJson = jsonService.sortJson(unsortedJson);

        String expectedSortedJson = "{\"a\":1,\"b\":2,\"c\":3}";
        assertEquals(expectedSortedJson, sortedJson);
    }

    @Test
    void testSortJson_InvalidJson() {
        String invalidJson = "{\"b\": 2, \"a\": , \"c\": 3}"; // Invalid JSON

        String sortedJson = jsonService.sortJson(invalidJson);
        assertEquals("Invalid JSON format: Unexpected character (", sortedJson.substring(0, 43));
    }
}
