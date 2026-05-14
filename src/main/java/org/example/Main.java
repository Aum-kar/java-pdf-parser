package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.ObjectExtractor;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.example.PdfParser.getRowData;

public class Main {
    public static void main(String[] args) {
        String filepath = "C:\\Users\\omkar\\IdeaProjects\\parse-pdf\\pdfsource\\eStatement-2024_06.pdf";
        try {
            File file = new File(filepath);
            PDDocument document = Loader.loadPDF(file);
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            int totalPages = document.getNumberOfPages();

            BufferedWriter writer =
                    new BufferedWriter(new FileWriter("C:\\Users\\omkar\\IdeaProjects\\parse-pdf\\pdfsource\\textOutputFiles\\test.csv"));


            System.out.println("Date\tValue Date\tDescription\tCheque\tDeposits\tWithdrawal\tBalance");
            for (int i = 1; i <= totalPages; i++) {
                List<Table> tables = sea.extract(extractor.extract(i));

                for (Table table : tables) {
                    List<List<RectangularTextContainer>> rows = table.getRows();

                    for (List<RectangularTextContainer> row : rows) {
                        List<String> cells = getRowData(row);
                        if (cells != null) {

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);

                            try {
                                LocalDate date = LocalDate.parse(cells.get(0), formatter);
                                LocalDate valueDate = LocalDate.parse(cells.get(1), formatter);
                                String description = cells.get(2);
                                String cheque = cells.get(3) == null ? "N/A" : cells.get(3);
                                BigDecimal deposit = cells.get(4).isEmpty() ? BigDecimal.ZERO : new BigDecimal(cells.get(4));
                                BigDecimal withdrawal = cells.get(5).isEmpty() ? BigDecimal.ZERO : new BigDecimal(cells.get(5));
                                BigDecimal balance = cells.get(6).isEmpty() ? BigDecimal.ZERO : new BigDecimal(cells.get(6));

                            writer.write(date +
                                    ", " + valueDate +
                                    ", " + description +
                                    ", " + cheque +
                                    ", " + deposit +
                                    ", " + withdrawal +
                                    ", " + balance + "\n"
                            );
                            } catch(Exception e) {
                                System.err.println("Failed to parse data: " + cells);
                            }
                        }
                    }
                }
            }
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
