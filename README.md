# MatrixChallenge

A Java Spring Boot application for performing operations on square matrices provided as CSV files.  
Supports **echo, invert, flatten, sum, and multiply** operations with robust validation and clear error handling.

## Features

- **Matrix Operations**
  - **Echo**: Returns the matrix as-is.
  - **Invert**: Transposes the matrix (rows become columns).
  - **Flatten**: Converts the matrix into a single comma-separated row.
  - **Sum**: Calculates the sum of all elements.
  - **Multiply**: Calculates the product of all elements, with overflow detection.

- **Validation**
  - Rejects non-square matrices.
  - Rejects empty rows, inconsistent columns, or non-numeric values.
  - Detects overflow during sum or multiplication.
  - Rejects unsupported file types.

- **Error Handling**
  - All invalid inputs throw `InvalidMatrixException`.
  - `GlobalExceptionHandler` converts exceptions to HTTP 400 responses with helpful messages.

---

## Assumptions

- Input matrices are **square** (rows = columns).  
- Elements are **integers only**.  
- Overflow in sum or product triggers `InvalidMatrixException`.  
- Input files are **CSV** (other formats can be added via the parser interface).  
- Focus is on **backend logic**, no frontend included.

---

## Design & Implementation Choices

1. **Parser Interface & Factory**
   - `MatrixParser` interface allows multiple formats (CSV, TSV, etc.).
   - `MatrixParserFactory` provides the parser dynamically based on file extension.

2. **Service Layer**
   - `MatrixService` implements all operations.
   - Parsing, square validation, and file type detection are centralized in private helpers.

3. **Exception Handling**
   - `InvalidMatrixException` signals invalid input or operation.
   - `GlobalExceptionHandler` ensures HTTP 400 with meaningful messages.

4. **Testing**
   - Each operation has its own test class.
   - `TestHelper` loads CSV files and preconfigures `MatrixService`.
   - Invalid cases are tested across all operations.

5. **Consistency**
   - All endpoints return **string representations**, including sum and multiply.

---

## Requirements

- Java 17+  
- Maven 3.8+  
- Spring Boot 3.x  
- JUnit 5  

---

## Setup & Running

1. **Clone the repository**

```bash
git clone https://github.com/mihirvadeyar/matrix-challenge.git
cd matrix-challenge
```

2. **Build the project**
```bash
mvn clean install
```

3. Run the Application
```bash
mvn spring-boot:run
```

### **API Endpoints**

## API Endpoints

All endpoints accept a **multipart file** (`file`) containing a square CSV matrix.

| Method | Endpoint     | Description                 |
|--------|------------|-----------------------------|
| POST   | `/echo`     | Returns the matrix as-is.   |
| POST   | `/invert`   | Transposes the matrix.      |
| POST   | `/flatten`  | Flattens into a single row. |
| POST   | `/sum`      | Returns the sum of elements.|
| POST   | `/multiply` | Returns the product of elements.|

**Example `curl` request:**

```bash
curl -F 'file=@/path/to/matrix.csv' http://localhost:8080/echo
```

### **Testing**


## Testing

Run all unit tests:

```bash
mvn test
```

## Testing Resources & Classes

### Test Classes

The project includes dedicated test classes for each operation:

- `EchoMatrixTest`
- `InvertMatrixTest`
- `FlattenMatrixTest`
- `SumMatrixTest`
- `MultiplyMatrixTest`
- `InvalidMatrixTest` (covers invalid input scenarios)

### Test Helper

- `TestHelper` handles service creation and loading of CSV files for tests.

### Test CSV Files

Make sure you have the following CSV files under `src/test/resources` for the unit tests:

- `matrix_3x3.csv`
- `matrix_4x4.csv`
- `matrix_1x1.csv`
- `matrix_2x2.csv`
- `matrix_5x5.csv`
- `matrix_3x3_negativeNumbers.csv`
- `matrix_2x2_largeNumbers.csv`
- `matrix_2x2_largeNegativeNumbers.csv`
- `matrix_empty.csv`
- `matrix_inconsistent.csv`
- `matrix_non_numeric.csv`
- `matrix_2x3.csv` (non-square)
- `matrix_4x1.csv` (non-square)
- `matrix_invalidType.txt` (unsupported file)

## Future Scope & Improvements

This project is designed to be simple, readable, and robust, but there are several directions for future enhancement:

- **Separation of Parsing and Validation**  
  - Currently, `CsvMatrixParser` performs basic validations while parsing.  
  - Future improvement: separate concerns into a dedicated `MatrixValidator` to handle:  
    - Empty matrices  
    - Non-numeric values  
    - Inconsistent columns  
    - Square matrix validation  
  - This would make parsers strictly responsible for parsing, improving reusability.

- **Extending to Multiple File Types**  
  - Currently, only CSV files are supported.  
  - Additional parsers (e.g., TSV, JSON, Excel) can be added by implementing `MatrixParser` and registering them in `MatrixParserFactory`.  
  - The controller can dynamically select the parser based on file extension or content type.

- **Custom Validation Rules**  
  - Validation could be configurable based on use case, e.g., allowing rectangular matrices, different numeric types, or matrix size limits.

- **Enhanced Error Reporting**  
  - Provide detailed error messages pointing to exact rows/columns of issues.  
  - Include helpful guidance for fixing invalid input files.

- **Performance and Scalability**  
  - For very large matrices, consider streaming processing or optimized numeric libraries to reduce memory usage and handle integer overflow more efficiently.
