# PDF Bank Statement Parser

A Java-based PDF bank statement parsing and transaction extraction project built using PDFBox and Tabula.

This project extracts tabular transaction data from digitally generated bank e-statements, normalizes it into structured `Transaction` objects, and generates validated financial summaries including deposits, withdrawals, and closing balances.

The project was developed as a standalone parser and data normalization pipeline focused on handling semi-structured financial PDF documents.

---

## Features

- Extracts tabular transaction data from PDF bank statements
- Uses Apache PDFBox for PDF loading and document processing
- Uses Tabula for table extraction
- Converts extracted rows into normalized `Transaction` domain objects
- Handles split balance columns caused by PDF formatting inconsistencies
- Skips repeated table headers and summary rows
- Preserves empty columns for consistent schema handling
- Normalizes numeric values for easier parsing and processing
- Processes multiple PDF statements in batch mode
- Generates statement summaries including:
  - total deposits
  - total withdrawals
  - closing balance
- Validates extracted totals against original PDF statements

---

## Architecture Flow

```text
PDF Statement
    ↓
Table Extraction (Tabula)
    ↓
Row Normalization
    ↓
Transaction Object Mapping
    ↓
Statement Summary Generation
````

---

## Tech Stack

* Java
* Maven
* Apache PDFBox
* Tabula

---

# Project Structure

```text
src/
 └── main/
     └── java/
         └── org/example/
             ├── Main.java
             ├── PdfParser.java
             └── Transaction.java
```

---

## Transaction Schema

Each transaction is normalized into the following structure:

| Field       | Description                       |
| ----------- | --------------------------------- |
| Date        | Transaction date                  |
| Value Date  | Settlement/value date             |
| Description | Transaction description           |
| Cheque      | Cheque number (if available)      |
| Deposit     | Deposit amount                    |
| Withdrawal  | Withdrawal amount                 |
| Balance     | Account balance after transaction |

---

## Example Output

```text
fileName                deposit     withdrawal     balance

estatement-2025_01.pdf  34767.00    37999.51       24908.55
estatement-2025_02.pdf  31289.70    29663.87       30856.83
estatement-2025_03.pdf  29279.00    12948.20       51514.63
```

---

## Dependencies

Add the following dependencies to `pom.xml`:

```xml
<dependencies>

    <dependency>
        <groupId>org.apache.pdfbox</groupId>
        <artifactId>pdfbox</artifactId>
        <version>3.0.7</version>
    </dependency>

    <dependency>
        <groupId>technology.tabula</groupId>
        <artifactId>tabula</artifactId>
        <version>1.0.5</version>
    </dependency>

</dependencies>
```

---

## Running the Project

### Clone the repository

```bash
git clone https://github.com/Aum-kar/java-pdf-parser.git
```

### Open the project

Open the project using IntelliJ IDEA or another Java IDE.

### Add PDF statements

Place PDF bank statement files inside a local directory.

Update the directory path in `Main.java`:

```java
File directory = new File("path/to/pdf/folder/");
```

### Run the application

Execute `Main.java`.

---

## Current Limitations

* Optimized for digitally generated bank statements
* Some long descriptions may be partially truncated depending on PDF layout
* Does not support scanned/image-based PDFs
* Parsing logic may require adjustments for different bank formats
* Footer/legal disclaimer text may occasionally be extracted and filtered during parsing

---

## Project Status

This project is considered feature-complete in its current form and serves as a standalone PDF parsing and transaction extraction pipeline.

Future development, database integration, and web-based workflows will be implemented separately in a new project.

---

## License

This project was developed for educational and personal use.

