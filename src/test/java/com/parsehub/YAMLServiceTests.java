package com.parsehub;

import com.parsehub.util.Format;
import com.parsehub.util.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.parsehub.service.YamlService;

import static org.junit.jupiter.api.Assertions.*;

class YamlServiceTest {

    private YamlService yamlService;

    @BeforeEach
    void setUp() {
        yamlService = new YamlService();
    }

    @Test
    void validateYaml_shouldReturnValid_whenYamlIsWellFormed() {
        String validYaml = """
            person:
              name: John Doe
              age: 30
              address:
                street: Main St
                city: New York
            """;

        ValidationResult result = yamlService.validateYaml(validYaml);
        assertTrue(result.isValid());
        assertTrue(result.getErrorMessages().isEmpty());
    }

    @Test
    void validateYaml_shouldReturnInvalid_whenYamlIsMalformed() {
        String invalidYaml = """
            person:
              name: John Doe
              age: 30
              address
                street: Main St
                city: New York
            """;

        ValidationResult result = yamlService.validateYaml(invalidYaml);
        assertFalse(result.isValid());
        assertFalse(result.getErrorMessages().isEmpty());
    }

    @Test
    void formatYaml_shouldReturnFormattedYaml_whenValidYamlProvided() {
        String validYaml = """
            person:
              name: John Doe
              age: 30
            """;

        String result = yamlService.formatYaml(validYaml, Format.SPACE_2);
        assertTrue(result.contains("name: John Doe"));
        assertTrue(result.contains("age: 30"));
    }
}
