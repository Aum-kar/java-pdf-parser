package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.ObjectExtractor;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PdfParser {

    static List<Transaction> processTransactions(File document) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            PDDocument pdf = Loader.loadPDF(document);
            ObjectExtractor extractor = new ObjectExtractor(pdf);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            int totalPages = pdf.getNumberOfPages();

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
                                String cheque = cells.get(3).isEmpty() ? null : cells.get(3);
                                BigDecimal deposit = cells.get(4).isEmpty() ? BigDecimal.ZERO : new BigDecimal(cells.get(4));
                                BigDecimal withdrawal = cells.get(5).isEmpty() ? BigDecimal.ZERO : new BigDecimal(cells.get(5));
                                BigDecimal balance = cells.get(6).isEmpty() ? BigDecimal.ZERO : new BigDecimal(cells.get(6));

                                Transaction transaction = new Transaction();
                                transaction.setDate(date);
                                transaction.setValueDate(valueDate);
                                transaction.setDescription(description);
                                transaction.setCheque(cheque);
                                transaction.setDeposit(deposit);
                                transaction.setWithdrawal(withdrawal);
                                transaction.setBalance(balance);

                                transactions.add(transaction);
                            } catch(Exception e) {
                                System.err.println("Failed to parse data: " + cells);
                            }
                        }
                    }
                }
            }
            pdf.close();
        } catch (IOException e) {
            System.err.println();
        }
        return transactions;
    }

    static List<String> getRowData(List<RectangularTextContainer> row) {
        List<String> cells = new ArrayList<>();

        /*
         * Build complete row first
         */
        for (RectangularTextContainer cell : row) {
            String text = cell.getText()
                    .replace("\r", "")
                    .replace("\n", "")
                    .trim();

            // remove commas only from numeric values
            if (text.matches("[0-9,]+\\.?[0-9]*"))
                text = text.replace(",", "");
            cells.add(text);
        }

        /*
         * Skip repeated page headers
         */
        boolean isHeader = (cells.contains("Value Date") || cells.contains("ValueDate"))
                        && cells.contains("Description")
                        && cells.contains("Withdrawal")
                        || cells.contains("BALANCE FORWARD");

        if (isHeader)
            return null;

        /*
         * Skip TOTAL row
         */
        boolean isTotal = cells
                .stream()
                .anyMatch(c -> c.equalsIgnoreCase("Total"));

        if (isTotal)
            return null;

        /*
         * Merge split balance column
         */
        if (cells.size() >= 8) {
            String secondLast = cells.get(cells.size() - 2);
            String last = cells.get(cells.size() - 1);

            if (secondLast.matches("\\d+")
                    && last.matches("\\d+\\.\\d+")) {

                String mergedBalance = secondLast + last;

                cells.remove(cells.size() - 1);
                cells.set(cells.size() - 1, mergedBalance);
            }
        }

        /*
         * Ensure exactly 7 columns
         */
        while (cells.size() < 7)
            cells.add("");

        return cells;
    }
}
