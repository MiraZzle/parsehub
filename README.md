# ParseHub Data Conversion & Validation Tool

ParseHub is a web-based application designed to handle various data formats (JSON, XML, YAML, CSV) and perform tasks like validation, formatting, conversion, and minification. It provides an API service for developers working with structured data.

## Features

- **Validation**: Validate JSON, XML, and YAML data.
- **Formatting**: Format data with custom indentation or minified versions.
- **Conversion**: Convert between JSON, XML, YAML, and CSV formats.
- **Web UI**: A Vaadin-based user interface for manual data input, output, and file uploads.

## Technologies

- **Backend**: Java Spring Boot
- **Frontend**: Vaadin Flow
- **Data Processing**: Jackson (for JSON, XML, CSV), SnakeYAML (for YAML)
- **Testing**: JUnit 5

## Getting Started

### Prerequisites

- **Java 17+**
- **Maven** or **Gradle** (for managing dependencies)
- **Node.js** (for frontend dependencies in Vaadin)

### Clone the Repository

```bash
git clone https://github.com/MiraZzle/parsehub.git
cd parsehub
```

### Running the Application

1. **Build the project** with Maven or Gradle:

   ```bash
   mvn clean install
   ```

2. **Run the application**:

   ```bash
   mvn spring-boot:run
   ```

3. Access the application at `http://localhost:8080/`.

### Running Tests

Run unit tests using:

```bash
mvn test
```

## File Upload/Download Features

- The application supports uploading `.json`, `.xml`, `.yaml`, and `.txt` files to parse and display content.
- Processed data can be downloaded as `.txt` files after conversion or formatting.

## API Routes

[Here](/docs/api.md) is a list of the available API routes.

## Contributing

Feel free to fork this repository and submit pull requests if you would like to contribute. Make sure to follow the project's code style and add relevant tests for new features or fixes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
