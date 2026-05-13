# PDF Bank Statement Parser

A Java-based PDF bank statement parser built using PDFBox and Tabula. The project extracts transaction data from e-statements and structures it into normalized rows containing transaction date, value date, description, cheque number, deposit amount, withdrawal amount, and balance.

## Features

* Extracts tabular transaction data from PDF bank statements
* Uses Apache PDFBox for PDF loading and processing
* Uses Tabula for table extraction
* Handles split balance columns caused by PDF formatting
* Skips repeated table headers and summary rows
* Preserves empty columns for consistent schema
* Normalizes numeric values for easier parsing and storage

## Tech Stack

* Java
* Maven
* Apache PDFBox
* Tabula

## Project Structure

```text
src/
 └── main/
     └── java/
         └── org/example/
             └── Main.java
```

## Output Schema

Each transaction row is normalized into the following structure:

| Column      | Description                       |
| ----------- | --------------------------------- |
| Date        | Transaction date                  |
| Value Date  | Settlement/value date             |
| Description | Transaction description           |
| Cheque      | Cheque number (if available)      |
| Deposit     | Deposit amount                    |
| Withdrawal  | Withdrawal amount                 |
| Balance     | Account balance after transaction |

## Example Output

```text
Date: 01 Apr 2026
Value Date: 01 Apr 2026
Description: UPI/609182062395/ BANARSI TEA AND SNACKS
Cheque:
Deposit:
Withdrawal: 20.00
Balance: 105730.63
```

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

## Running the Project

1. Clone the repository

```bash
git clone https://github.com/Aum-kar/java-pdf-parser.git
```

2. Open the project in IntelliJ IDEA or another Java IDE

3. Add a PDF bank statement file

4. Update the file path in `Main.java`

```java
String filepath = "path/to/statement.pdf";
```

5. Run the application

## Current Limitations

* Optimized for digitally generated bank statements
* Some long descriptions may be partially truncated depending on PDF layout
* Does not currently support scanned/image-based PDFs
* Parsing logic may need adjustments for different bank formats

## Future Improvements

* Export parsed data to CSV/TSV
* Database integration
* Email attachment ingestion
* Multi-bank format support
* OCR support for scanned statements
* REST API integration
* Transaction categorization and analytics

## License

This project is under development and intended for educational and personal use.
