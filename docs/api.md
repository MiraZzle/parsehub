## API routes

### JSON Routes

- **POST** `/api/v1/json/validate` — Validate a JSON string.
- **POST** `/api/v1/json/format/{format}` — Format a JSON string using the specified format (e.g., SPACE_2, SPACE_4).
- **POST** `/api/v1/json/minify` — Minify a JSON string.
- **POST** `/api/v1/json/convert/xml` — Convert JSON to XML.
- **POST** `/api/v1/json/convert/yaml` — Convert JSON to YAML.
- **POST** `/api/v1/json/convert/csv` — Convert JSON to CSV.
- **POST** `/api/v1/json/sort` — Sort JSON keys alphabetically.

### XML Routes

- **POST** `/api/v1/xml/validate` — Validate an XML string.
- **POST** `/api/v1/xml/minify` — Minify an XML string.
- **POST** `/api/v1/xml/convert/json` — Convert XML to JSON.
- **POST** `/api/v1/xml/convert/yaml` — Convert XML to YAML.
- **POST** `/api/v1/xml/convert/csv` — Convert XML to CSV.

### YAML Routes

- **POST** `/api/v1/yaml/validate` — Validate a YAML string.
- **POST** `/api/v1/yaml/minify` — Minify a YAML string.
- **POST** `/api/v1/yaml/convert/json` — Convert YAML to JSON.
- **POST** `/api/v1/yaml/convert/xml` — Convert YAML to XML.
- **POST** `/api/v1/yaml/convert/csv` — Convert YAML to CSV.
