package com.parsehub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.parsehub.util.ValidationResult;
import com.parsehub.util.Format;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import com.parsehub.util.ConversionType;

@Service
public class XmlService {

    public XmlService() {
    }

    // XML Validation against a Schema (XSD)
    public ValidationResult validateXml(String xml) {
        ValidationResult result = new ValidationResult();

        try {
            // Just check if the XML is well-formed (no XSD validation)
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.readTree(new StringReader(xml));  // If well-formed, this will succeed

            result.setValid(true);
        } catch (IOException e) {
            result.setValid(false);
            result.addErrorMessage("XML Parsing Error: " + e.getMessage());
        }

        return result;
    }

    // Formatting XML based on pretty-print or minify styles
    public String formatXml(String xml, Format format) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode tree = xmlMapper.readTree(new StringReader(xml));

            switch (format) {
                case SPACE_2:
                case SPACE_3:
                case SPACE_4:
                    return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree); // Pretty-print
                case COMPACT:
                default:
                    return xmlMapper.writeValueAsString(tree); // Minified XML
            }
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }

    public String convertData(String xml, ConversionType targetType) {
        try {
            if (targetType == ConversionType.XML) {
                return xml; // If the target is XML, no conversion needed
            }

            switch (targetType) {
                case JSON:
                    return convertXmlToJson(xml);
                case YAML:
                    return convertXmlToYaml(xml);
                case CSV:
                    return convertXmlToCsv(xml);
                default:
                    return xml;
            }
        } catch (Exception e) {
            return "Invalid XML format: " + e.getMessage();
        }
    }

    // Convert XML to JSON
    public String convertXmlToJson(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(new StringReader(xml));
            ObjectMapper jsonMapper = new ObjectMapper();
            return jsonMapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }

    // Convert XML to YAML (via JSON conversion)
    public String convertXmlToYaml(String xml) {
        try {
            // First, convert XML to JSON
            String json = convertXmlToJson(xml);
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.readTree(json);

            // Convert JSON to YAML
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            Object object = jsonMapper.convertValue(jsonNode, Object.class);
            return yaml.dump(object);
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }

    // Convert XML to CSV
    public String convertXmlToCsv(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(new StringReader(xml));

            CsvMapper csvMapper = new CsvMapper();
            CsvSchema.Builder schemaBuilder = CsvSchema.builder();

            if (!jsonNode.isArray()) {
                throw new IllegalArgumentException("XML must represent an array-like structure for CSV conversion");
            }

            // Build schema from the first element (assuming JSON-like data structure)
            JsonNode firstElement = jsonNode.elements().next();
            firstElement.fieldNames().forEachRemaining(schemaBuilder::addColumn);

            CsvSchema schema = schemaBuilder.build().withHeader();
            return csvMapper.writer(schema).writeValueAsString(jsonNode);
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }

    // Minify XML (remove indentation and whitespace)
    public String minifyXml(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode tree = xmlMapper.readTree(new StringReader(xml));
            return xmlMapper.writeValueAsString(tree); // Minified XML
        } catch (IOException e) {
            return "Invalid XML: " + e.getMessage();
        }
    }
}
