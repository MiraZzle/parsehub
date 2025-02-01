# ParseHub Data Conversion & Validation Tool

ParseHub is a web-based application designed to handle various data formats (JSON, XML, YAML, CSV) and perform tasks like validation, formatting, conversion, and minification. It provides an API service for developers working with structured data.

![ui showcase](/docs/assets/ui_showcase.png)

## Features

| Feature       | Description |
|--------------|------------|
| **Validation** | Validate JSON, XML, and YAML data. |
| **Formatting** | Format data with custom indentation or minified versions. |
| **Conversion** | Convert between JSON, XML, YAML, and CSV formats. |
| **Web UI** | A Vaadin-based user interface for manual data input, output, and file uploads. |

## Technologies

| Component  | Technology Used |
|------------|----------------|
| **Backend** | Java Spring Boot |
| **Frontend** | Vaadin Flow |
| **Data Processing** | Jackson (JSON, XML, CSV), SnakeYAML (YAML) |
| **Testing** | JUnit 5 |

## Getting Started

### Prerequisites

| Requirement | Description |
|-------------|------------|
| **Java** | Java 17+ |
| **Build Tools** | Maven or Gradle (for managing dependencies) |
| **Frontend** | Node.js (for frontend dependencies in Vaadin) |

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

3. Access the application at **`http://localhost:5000/`**.

### Running Tests

Run unit tests using:

```bash
mvn test
```

## API Routes

A list of available API routes can be found [here](/docs/api.md).

## Contributing

Feel free to fork this repository and submit pull requests if you would like to contribute.

---

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for more details.
